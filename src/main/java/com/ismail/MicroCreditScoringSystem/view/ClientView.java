package main.java.com.ismail.MicroCreditScoringSystem.view;

import main.java.com.ismail.MicroCreditScoringSystem.Algorithlms.ScoringAlgorithm;
import main.java.com.ismail.MicroCreditScoringSystem.controller.PersonController;
import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientView {
    ScoringAlgorithm sa = new ScoringAlgorithm();
    //Add a new client (employee or professional).
    //
    //Modify salary, position, or income.
    //
    //View client’s score and credit history.
    public void clientMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice ;
        do {
            System.out.println("1 => Add New Client");
            System.out.println("2 => Modify Client");
            System.out.println("3 => Delete Client");
            System.out.println("4 => View Client Profile");
            System.out.println("5 => List All Clients");
            System.out.println("0 => Back to Main Menu");
            choice = scanner.nextInt();
            new PersonController(choice);
        }while (choice != 0);
    }

    public void displayEmployee(Person person) {
        if (!(person instanceof Employee)) {
            System.out.println("The provided object is not an Employee.");
            return;
        }
        Employee employee = (Employee) person;

        System.out.println("----- Employee Details -----");
        System.out.println("ID: " + employee.getId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Birthday: " + employee.getBirthday());
        System.out.println("City: " + employee.getCity());
        System.out.println("Investment: " + employee.isInvestment());
        System.out.println("Placement: " + employee.isPlacement());
        System.out.println("Marital Status: " + employee.getFamilyStatus());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Seniority: " + employee.getSeniority() + " years");
        System.out.println("Contract Type: " + employee.getContractType());
        System.out.println("Sector: " + employee.getSector());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Children Count: " + employee.getChildrenCount());
        System.out.println("----------------------------");
    }

    public void displayProfessional(Person person) {
        if (!(person instanceof Professional)) {
            System.out.println("The provided object is not a Professional.");
            return;
        }
        Professional professional = (Professional) person;

        System.out.println("----- Professional Details -----");
        System.out.println("ID: " + professional.getId());
        System.out.println("First Name: " + professional.getFirstName());
        System.out.println("Last Name: " + professional.getLastName());
        System.out.println("Birthday: " + professional.getBirthday());
        System.out.println("City: " + professional.getCity());
        System.out.println("Investment: " + professional.isInvestment());
        System.out.println("Placement: " + professional.isPlacement());
        System.out.println("Marital Status: " + professional.getFamilyStatus());
        System.out.println("Income: " + professional.getIncome());
        System.out.println("Tax Registration: " + professional.getTaxRegistration());
        System.out.println("Activity Sector: " + professional.getActivitySector());
        System.out.println("Activity: " + professional.getActivity());
        System.out.println("Children Count: " + professional.getChildrenCount());
        System.out.println("--------------------------------");
    }

    public void displayAllClients(ArrayList<Person> clients) {
        for (Person client : clients) {
            System.out.println("score : ");
            sa.totalScore(client);

            if (client instanceof Employee) {
                displayEmployee(client);
            } else if (client instanceof Professional) {
                displayProfessional(client);
            } else {
                System.out.println("Unknown client type: " + client);
            }
        }
    }

}
