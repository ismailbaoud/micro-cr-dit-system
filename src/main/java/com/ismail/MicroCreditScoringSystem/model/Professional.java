package main.java.com.ismail.MicroCreditScoringSystem.model;

import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ActivitySector;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;

import java.time.LocalDate;
import java.util.Date;

public class Professional extends Person{
    private Double income;
    private String taxRegistration;
    private ContractType contractType;
    private ActivitySector activitySector;
    private String activity;
    private Integer seniority;



    public Professional(String firstName, String lastName, LocalDate birthday, String city, boolean investment, boolean placement, String familyStatus, String activity, ActivitySector activitySector, String taxRegistration, Double income, Integer childrenCount,ContractType contractType) {
        super(firstName, lastName, birthday, city, investment, placement, familyStatus ,childrenCount);
        this.activity = activity;
        this.activitySector = activitySector;
        this.taxRegistration = taxRegistration;
        this.income = income;
        this.contractType = contractType;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getTaxRegistration() {
        return taxRegistration;
    }

    public void setTaxRegistration(String taxRegistration) {
        this.taxRegistration = taxRegistration;
    }

    public ActivitySector getActivitySector() {
        return activitySector;
    }

    public void setActivitySector(ActivitySector activitySector) {
        this.activitySector = activitySector;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }
}
