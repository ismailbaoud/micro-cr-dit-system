package main.java.com.ismail.MicroCreditScoringSystem.Algorithlms;

import com.sun.org.glassfish.external.amx.AMX;
import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.EmployeeSector;

import java.time.LocalDate;
import java.time.Period;

public class ScoringAlgorithm {

    public Integer totalScore(Person client) {

        if(client != null) {
            Integer sp = stabiliteProfessionnelle(client) ;
            Integer cf = capaciteFinanciere(client);
            Integer rc = relationClient(client);
            Integer cc = criteresComplementaires(client);
            return sp+cf+rc+cc;
        }else {
            System.out.println("no client exists");
        }
        return 0;
    }

    // --------- STABILITE PROFESSIONNELLE ----------
    public int stabiliteProfessionnelle(Person client) {
        int score = 0;

        if (client instanceof Employee) {
            Employee emp = (Employee) client;

            if (emp.getContractType() != null) {
                if (emp.getContractType() == ContractType.CDI) {
                    if (emp.getSector() == EmployeeSector.PUBLIC) score += 25;
                    else if (emp.getSector() == EmployeeSector.LARGE_COMPANY) score += 15;
                    else if (emp.getSector() == EmployeeSector.SME) score += 12;
                } else if (emp.getContractType() == ContractType.CDD) {
                    score += 10;
                }
            }

            if (emp.getSeniority() != null) {
                int seniority = emp.getSeniority();
                if (seniority < 1) score += 5;
                else if (seniority == 1) score += 1;
                else if (seniority < 5) score += 3;
                else score += 5;
            }

        } else if (client instanceof Professional) {
            Professional pro = (Professional) client;

            if (pro.getContractType() != null) {
                if ("STABLE_LIBERAL_PROFESSION".equalsIgnoreCase(pro.getContractType().name())) {
                    score += 18;
                } else if ("SELF_EMPLOYED".equalsIgnoreCase(pro.getContractType().name())) {
                    score += 12;
                }
            }

            if (pro.getSeniority() != null) {
                int seniority = pro.getSeniority();
                if (seniority < 1) score += 5;
                else if (seniority == 1) score += 1;
                else if (seniority < 5) score += 3;
                else score += 5;
            }
        }

        return score;
    }


    // --------- CAPACITE FINANCIERE ----------
    public int capaciteFinanciere(Person client) {
        double amount;
        if(client instanceof Employee) {
            amount = ((Employee) client).getSalary();
        }else {
            amount = ((Professional) client).getIncome();
        }
        int score = 0;
        if (amount < 3000) score += 10;
        else if (amount >= 3000 && amount < 5000) score += 15;
        else if (amount >= 5000 && amount < 8000) score += 20;
        else if (amount >= 8000 && amount < 10000) score += 25;
        else if (amount >= 10000) score += 30;
        return score;
    }

    // --------- RELATION CLIENT ----------
    public int relationClient(Person client) {

        int score = 0;
        Integer age = Period.between(client.getBirthday(), LocalDate.now()).getYears();;
        if (age >= 18 && age < 25) score += 4;
        else if (age >= 25 && age < 35) score += 8;
        else if (age >= 35 && age < 55) score += 10;
        else if (age >= 55) score += 6;

        if ("married".equalsIgnoreCase(client.getFamilyStatus())) score += 3;
        else if ("single".equalsIgnoreCase(client.getFamilyStatus())) score += 2;

        if (client.getChildrenCount() == 0) score += 2;
        else if (client.getChildrenCount() == 1 || client.getChildrenCount() == 2) score += 1;

        return score;
    }

    // --------- CRITERES COMPLEMENTAIRES ----------
    public int criteresComplementaires(Person client) {
        int score = 0;
        if (client.isInvestment() || client.isPlacement()) score += 10;
        return score;
    }
}
