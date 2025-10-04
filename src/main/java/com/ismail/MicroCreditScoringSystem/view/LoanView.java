package main.java.com.ismail.MicroCreditScoringSystem.view;


import main.java.com.ismail.MicroCreditScoringSystem.controller.LoanController;
import main.java.com.ismail.MicroCreditScoringSystem.model.Loan;

import java.util.ArrayList;
import java.util.Scanner;

public class LoanView {
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
            System.out.println("2 => handle the manual review loans ");
            System.out.println("3 => display manual review loans ");
            System.out.println("4 => display general statistics ");
            System.out.println("5 => pay credit");
            System.out.println("0 => Go back");
            choice = scanner.nextInt();
            new LoanController(choice);
        }while (choice != 0);
    }


    public void displayLoans(ArrayList<Loan> loans) {
        for(Loan loan : loans) {
            System.out.println("Id : " + loan.getId() + " , Decision : "+loan.getDecision() + " , amount : " + loan.getMontantOctroye());
        }
    }
}
