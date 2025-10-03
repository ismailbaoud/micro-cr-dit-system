package main.java.com.ismail.MicroCreditScoringSystem.controller;

import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.Decision;
import main.java.com.ismail.MicroCreditScoringSystem.service.LoanService;
import main.java.com.ismail.MicroCreditScoringSystem.service.PersonService;

import java.time.LocalDate;
import java.util.*;

public class LoanController {
    private final Scanner scanner = new Scanner(System.in);
    private final LoanService loanService = new LoanService();
    PersonService personService = new PersonService();
    public LoanController(int choice) {
        manageOptions(choice);
    }

    private void manageOptions(int choice) {
        switch (choice) {
            case 1: createLoan(); break;
            case 2: payLoan(); break;
            default: System.out.println("Invalid option!");
        }
    }

    public void payLoan() {
        System.out.println("enter the client credit id : ");
        UUID id = UUID.fromString(scanner.next());
        loanService.CreatePayment(id);
    }
    private void createLoan() {
        System.out.print("Please enter the employee ID (or leave empty): ");
        String employeeId = scanner.nextLine().trim();
        if (employeeId.isEmpty()) employeeId = null;

        System.out.print("Please enter the professional ID (or leave empty): ");
        String professionalId = scanner.nextLine().trim();
        if (professionalId.isEmpty()) professionalId = null;
        boolean isExist = false;
        Person client = null;
        if (employeeId != null) {
            client = personService.getClient(UUID.fromString(employeeId));
            isExist = loanService.isExist(client.getId());

        } else if (professionalId != null) {
            client = personService.getClient(UUID.fromString(professionalId));
        } else {
            System.out.println("Error: You must enter at least an Employee ID or a Professional ID.");
            return;
        }

        System.out.print("Application date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Requested amount: ");
        double requested = Double.parseDouble(scanner.nextLine().trim());
        double granted = getGranted(client, isExist);
        if(granted >= requested) {
            granted = requested;
        }else{
            System.out.println("Do you accept this amount "+ granted +"?");
            System.out.println("1 => Yes");
            System.out.println("2 => No");
            Integer choice = scanner.nextInt();
            if(choice == 2) {
                System.out.println("annulled");
                return;
            }
        }

        System.out.print("Duration (months): ");
        int duration = scanner.nextInt();

        Decision decision = chooseDecision(client,isExist);

        loanService.createLoan(employeeId, professionalId, date, requested, granted, duration, decision);
    }

    public Decision chooseDecision(Person client, boolean existingClient) {
        int score =  client.getScore();

        if (!existingClient) {
            if (score >= 80) return Decision.IMMEDIATE_APPROVAL;
            else if (score >= 70) return Decision.MANUAL_REVIEW;
            else return Decision.AUTOMATIC_REJECTION;
        } else {
            if (score >= 80) return Decision.IMMEDIATE_APPROVAL;
            else if (score >= 60) return Decision.MANUAL_REVIEW;
            else return Decision.AUTOMATIC_REJECTION;
        }
    }

    public double getGranted(Person client, boolean existingClient) {
        int score = client.getScore();

        double income = (client instanceof Employee) ? ((Employee) client).getSalary()
                : ((Professional) client).getIncome();
        Integer seniority = (client instanceof Employee) ? ((Employee) client).getSeniority()
                : ((Professional) client).getSeniority();

        if (!existingClient) {
            if (score >= 70 && seniority > 2) {
                return income * 4;
            }
        } else {
            if (score >= 60) {
                return income * 7;
            }
        }

        return 0.0;
    }
}
