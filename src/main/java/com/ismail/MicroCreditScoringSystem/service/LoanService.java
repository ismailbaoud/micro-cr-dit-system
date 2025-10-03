package main.java.com.ismail.MicroCreditScoringSystem.service;

import main.java.com.ismail.MicroCreditScoringSystem.model.Installment;
import main.java.com.ismail.MicroCreditScoringSystem.model.Loan;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.Decision;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;
import main.java.com.ismail.MicroCreditScoringSystem.repository.InstallmentRepository;
import main.java.com.ismail.MicroCreditScoringSystem.repository.LoanRepository;
import main.java.com.ismail.MicroCreditScoringSystem.repository.PersonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoanService {
    private LoanRepository loanRepository = new LoanRepository();
    private InstallmentRepository installmentRepository = new InstallmentRepository();
    private PersonRepository personRepository = new PersonRepository();
    public void createLoan(String employeeId, String professionalId, LocalDate applicationDate, double requestedAmount, double amountGranted, int durationInMonths, Decision decision) {
        Loan loan = new Loan(employeeId, professionalId, requestedAmount, amountGranted, (amountGranted * 0.5), durationInMonths, decision);
        Double monthlyPayment = loan.getMontantOctroye() / durationInMonths;
        System.out.println(monthlyPayment);
        Installment installment = new Installment(monthlyPayment, loan.getDateDeCredit(), null);
        if (loanRepository.createLoan(loan)) {
            if (loan.getDecision().equals(Decision.IMMEDIATE_APPROVAL) && installmentRepository.createInstallments(installment, loan, loan.getDureeEnMois())) {
                System.out.println("installement creted with success");
            }
            System.out.println("Loan created successfully!");
        } else {
            System.out.println("Failed to create loan.");
        }
    }

    public boolean isExist(UUID clientId) {
        return loanRepository.getLoans().stream().anyMatch(loan -> clientId.toString().equals(loan.getProfrssional_id()) || clientId.toString().equals(loan.getEmployee_id()));
    }


    public void changeStatus() {
        try {
            ArrayList<Loan> loans = loanRepository.getLoansWithInstallments();
            LocalDateTime now = LocalDateTime.now();

            for (Loan loan : loans) {
                for (Installment installment : loan.getInstallments()) {
                    LocalDateTime dueDateTime = installment.getDueDate().atStartOfDay();
                    long daysLate = ChronoUnit.DAYS.between(dueDateTime, now);

                    Person client = null;

                    String proId = loan.getProfrssional_id();
                    String empId = loan.getEmployee_id();

                    Person pro = null;
                    Person emp = null;

                    if (proId != null && !proId.isEmpty()) {
                        pro = personRepository.getPerson(UUID.fromString(proId));
                    }

                    if (empId != null && !empId.isEmpty()) {
                        emp = personRepository.getPerson(UUID.fromString(empId));
                    }

                    if (pro != null) {
                        client = pro;
                    } else {
                        client = emp;
                    }
                    if (dueDateTime.isBefore(now)) {

                        if (installment.getStatus() == PaymentStatus.NON) {
                            if (daysLate >= 5 && daysLate <= 30) {

                                installment.setStatus(PaymentStatus.LATE);
                                installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.LATE);
                                personRepository.updateScoring(client, 1);
                                System.out.println("Installment " + installment.getId() + " is LATE (" + daysLate + "s : late)");
                            }
                        } else if (installment.getStatus() == PaymentStatus.LATE) {

                            if (daysLate >= 30) {

                                installment.setStatus(PaymentStatus.UNPAID_NOT_SETTLED);
                                installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.UNPAID_NOT_SETTLED);
                                personRepository.updateScoring(client, 10);
                                System.out.println("Installment " + installment.getId() + " is LATE (" + daysLate + "s  : UNPAID NOT SETTLED");

                            }
                        }
                    }
                }
            }
        } catch (ClassCastException e) {
            System.out.println("service : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void CreatePayment(UUID loanId) {
        List<Installment> installments = loanRepository.getLoansWithInstallments()
                .stream()
                .filter(a -> String.valueOf(loanId).equals(a.getProfrssional_id())
                        || String.valueOf(loanId).equals(a.getEmployee_id()))
                .flatMap(a -> {
                    return a.getInstallments() != null
                            ? a.getInstallments().stream()
                            : Stream.empty();
                })
                .collect(Collectors.toList());

        Loan loanOpt = loanRepository.getLoansWithInstallments()
                .stream()
                .filter(a -> String.valueOf(loanId).equals(a.getProfrssional_id())
                        || String.valueOf(loanId).equals(a.getEmployee_id()))
                .findFirst().orElse(null);
        assert loanOpt != null;
        Person client = personRepository.getPerson(UUID.fromString(loanOpt.getProfrssional_id() != null ? loanOpt.getProfrssional_id() : loanOpt.getEmployee_id()));

        for(Installment installment : installments) {
            LocalDateTime now = LocalDateTime.now();
            UUID id = installment.getId();
            LocalDateTime dueDateTime = installment.getDueDate().atStartOfDay();
            if (dueDateTime.isBefore(now)) {
                if (installment != null) {
                    if (installment.getStatus().equals(PaymentStatus.NON)) {
                        installmentRepository.payInstallment(id);
                        personRepository.updateScoring(client, 10);
                        installmentRepository.updateInstallmentStatus(id, PaymentStatus.PAID_ON_TIME);
                    } else if (installment.getStatus().equals(PaymentStatus.LATE)) {
                        installmentRepository.payInstallment(id);
                        installmentRepository.updateInstallmentStatus(id, PaymentStatus.PAID_LATE);
                        personRepository.updateScoring(client, 3);
                    } else if (installment.getStatus().equals(PaymentStatus.UNPAID_NOT_SETTLED)) {
                        installmentRepository.payInstallment(id);
                        installmentRepository.updateInstallmentStatus(id, PaymentStatus.UNPAID_SETTLED);
                    } else {
                        System.out.println("this credit is already paid");
                    }
                }
            }
        }
    }


}
