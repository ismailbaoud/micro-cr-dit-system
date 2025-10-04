package main.java.com.ismail.MicroCreditScoringSystem.view;

import java.util.Scanner;

public class Menu {

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        Integer choice;
        do {
            System.out.println("1 => Client Management");
            System.out.println("2 => Credit & Score Management");
            System.out.println("0 => Exit");
            System.out.print("Please enter your choice : ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 :
                    new ClientView().clientMenu();
                    break;
                case 2 :
                    new LoanView().creditMenu();
                    break;
                case 3 :
                    new PaymentTrackingMenu().paymentTrackingMenu();
                    break;
                case 4 :
                    new DecisionEngineMenu().DecisionEngineMenu();
                    break;
                case 5 :
                    new AnalyticsMenu().analyticsMenu();
                    break;
                case 0 :
                    System.out.println("Good bye !");
                    break;
                default:
                    System.out.println("\nInvalid choice please try valid choice !!\n");
                    break;
            }
        }while (choice != 0);
    }

}
