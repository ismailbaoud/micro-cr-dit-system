package main.java.com.ismail.MicroCreditScoringSystem.view;

import java.util.Scanner;

public class AnalyticsMenu {
    //Filter clients based on multiple criteria.
    //
    //Sort and rank clients.
    //
    //Generate lists for marketing campaigns.
    public void analyticsMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice;
        do {
            System.out.println("1 => Search Eligible Clients (e.g., real estate loan)");
            System.out.println("2 => Identify High-Risk Clients (top 10)");
            System.out.println("3 => Sort Clients by Score / Income / Seniority");
            System.out.println("4 => View Clients by Employment Type");
            System.out.println("5 => Prepare Campaign Target List");
            System.out.println("0 => Back to Main Menu");
            choice = scanner.nextInt();
        }while (choice != 0);
    }

}
