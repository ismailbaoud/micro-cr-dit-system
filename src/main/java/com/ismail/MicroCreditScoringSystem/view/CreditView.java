package main.java.com.ismail.MicroCreditScoringSystem.view;


import main.java.com.ismail.MicroCreditScoringSystem.controller.LoanController;

import java.util.Scanner;

public class CreditView {
    //Calculate score automatically based on stability, financial capacity, history, etc.
    //
    //Check eligibility thresholds.
    //
    //Request a loan and see granted amount.
    public void creditMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice;
        do {
            System.out.println("1 => add new credit");
            System.out.println("2 => pay credit");
            choice = scanner.nextInt();
            new LoanController(choice);
        }while (choice != 0);
    }
}
