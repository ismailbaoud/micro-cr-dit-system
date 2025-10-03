package main.java.com.ismail.MicroCreditScoringSystem.jobs;

import main.java.com.ismail.MicroCreditScoringSystem.service.LoanService;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonthlyTask {



    public void run() {
        try {
            LoanService loanService = new LoanService();
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable task = () -> {
                if (LocalDate.now().getDayOfMonth() == 3) {
                    loanService.changeStatus();
                }
            };

            scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
        } catch (ClassCastException e) {
            System.out.println("job : "+e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}