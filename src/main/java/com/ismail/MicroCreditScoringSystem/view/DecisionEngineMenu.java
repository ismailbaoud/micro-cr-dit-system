package main.java.com.ismail.MicroCreditScoringSystem.view;

import java.util.Scanner;

public class DecisionEngineMenu {
    //Automatic approval if score ≥ 75.
    //
    //Manual review for scores 50–74.
    //
    //Automatic refusal if score < 50.
    public void DecisionEngineMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice;
        do {
            System.out.println("1 => Evaluate Loan Decision for Client");
            System.out.println("2 => View Decision Status");
            System.out.println("0 => Back to Main Menu");
            choice = scanner.nextInt();
        }while (choice != 0);
    }
}
