package main.java.com.ismail.MicroCreditScoringSystem.view;

import java.util.Scanner;

public class PaymentTrackingMenu {
    //Create monthly installments after credit approval.
    //
    //Record on-time, late, or unpaid payments.
    //
    //Update incidents and adjust client score dynamically.
    public void paymentTrackingMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice;
        do {
            System.out.println("1 => Generate Installments for Credit");
            System.out.println("2 => Record a Payment");
            System.out.println("3 => View Payment History");
            System.out.println("0 => Back to Main Menu");
            choice = scanner.nextInt();
        }while (choice != 0);
    }
}
