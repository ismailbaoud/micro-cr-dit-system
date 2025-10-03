package main.java.com.ismail.MicroCreditScoringSystem.app;

import main.java.com.ismail.MicroCreditScoringSystem.config.ConnectionDB;
import main.java.com.ismail.MicroCreditScoringSystem.jobs.MonthlyTask;
import main.java.com.ismail.MicroCreditScoringSystem.view.Menu;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Menu menu = new Menu();
    public static void main(String[] args) {
        MonthlyTask monthlyTask = new MonthlyTask();

        monthlyTask.run();

        menu.menu();
    }
}