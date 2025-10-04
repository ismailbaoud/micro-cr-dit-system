package main.java.com.ismail.MicroCreditScoringSystem.service;

import main.java.com.ismail.MicroCreditScoringSystem.model.*;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.Decision;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;
import main.java.com.ismail.MicroCreditScoringSystem.repository.InstallmentRepository;
import main.java.com.ismail.MicroCreditScoringSystem.repository.LoanRepository;
import main.java.com.ismail.MicroCreditScoringSystem.repository.PersonRepository;
import java.lang.Integer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                                personRepository.updateScoring(client, 1,"substraction");
                                System.out.println("Installment " + installment.getId() + " is LATE (" + daysLate + "s : late)");
                            }
                        } else if (installment.getStatus() == PaymentStatus.LATE) {

                            if (daysLate >= 30) {

                                installment.setStatus(PaymentStatus.UNPAID_NOT_SETTLED);
                                installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.UNPAID_NOT_SETTLED);
                                personRepository.updateScoring(client, 10, "substraction");
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

    public void createPayment(UUID loanId) {
        Loan loan = loanRepository.getLoansWithInstallments()
                .stream()
                .filter(l -> loanId.equals(l.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Loan not found for ID: " + loanId));


        List<Installment> installments = loan.getInstallments() != null
                ? loan.getInstallments()
                : Collections.emptyList();

        String clientId = loan.getProfrssional_id() != null
                ? loan.getProfrssional_id()
                : loan.getEmployee_id();

        Person client = getClient(clientId);
        if (client == null) {
            System.err.println("Client not found for ID: " + clientId);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        UUID clientUuid = UUID.fromString(clientId);

        for (Installment installment : installments) {
            if (installment.getDueDate().atStartOfDay().isBefore(now)) {
                System.out.println(client.getScore() + " ," + installment.getMonthlyPayment()+ " , "+clientUuid);
                processInstallment(installment, client, clientUuid);
            }
        }
    }

    private Person getClient(String clientId) {
        if (clientId == null) {
            System.err.println("Client ID is null");
            return null;
        }
        try {
            UUID uuid = UUID.fromString(clientId);
            return personRepository.getPerson(uuid);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID for client ID: " + clientId);
            return null;
        }
    }

    private void processInstallment(Installment installment, Person client, UUID clientId) {
        switch (installment.getStatus()) {
            case NON:
                installmentRepository.payInstallment(installment.getId());
                personRepository.updateScoring(client, 10, "addition");
                installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.PAID_ON_TIME);
                break;
            case LATE:
                installmentRepository.payInstallment(installment.getId());
                personRepository.updateScoring(client, 3, "addition");
                installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.PAID_LATE);
                break;
            case UNPAID_NOT_SETTLED:
                if (installmentRepository.payInstallment(installment.getId())){
                    System.out.println("payment date changed !");
                }
                if(installmentRepository.updateInstallmentStatus(installment.getId(), PaymentStatus.UNPAID_SETTLED)){
                    System.out.println("status updated");
                };

                break;
            default:
                System.out.println("This installment is already paid");
        }
    }

    public void changeStatusManual(UUID loanId , Decision newDecision) {
         if(loanRepository.changeStatusManual(loanId, newDecision)) {
             System.out.println("done ... , the status changed to : "+ newDecision);
         }
    }

    public ArrayList<Loan> getLoansBystatus() {
        return loanRepository.getLoans().stream().filter(a -> a.getDecision().equals(Decision.MANUAL_REVIEW)).collect(Collectors.toCollection(ArrayList::new));

    }

    public Loan getLoin(UUID id) {
        return loanRepository.getLoans().stream().filter(a -> a.getId().equals(id)).findFirst().get();
    }

    public void AverageScore() {
        Integer totalClients = personRepository.getAllClients().toArray().length;
        Double scoreTotal  = personRepository.getAllClients().stream().mapToDouble(Person::getScore).sum();
        Double avScore =  (scoreTotal / totalClients);
        Integer totalEmp = personRepository.getAllClients().stream().filter(a ->a instanceof Employee).toArray().length;
        Integer totalPro = personRepository.getAllClients().stream().filter(a -> a instanceof Professional).toArray().length;

        System.out.println("the total Employees is : " + totalEmp);
        System.out.println("the total Professionals is : " + totalPro);
        System.out.println("the total score of clients is : " + scoreTotal);
        System.out.println("the number of clients is : " + totalClients);
        System.out.println("the average score is :" + avScore);
    }


}
