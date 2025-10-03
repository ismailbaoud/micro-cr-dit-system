package main.java.com.ismail.MicroCreditScoringSystem.model;

import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.EmployeeSector;

import java.time.LocalDate;
import java.util.Date;

public class Employee extends Person {
    private Double salary ;
    private Integer seniority;
    private ContractType contractType;
    private EmployeeSector sector;
    private String position;


    public Employee(String firstName, String lastName, LocalDate birthday, String city, boolean investment, boolean placement, String familyStatus, Double salary, Integer seniority, ContractType contractType, EmployeeSector sector, String position, Integer childrenCount) {
        super(firstName, lastName, birthday, city, investment, placement, familyStatus, childrenCount);
        this.salary = salary;
        this.seniority = seniority;
        this.contractType = contractType;
        this.sector = sector;
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public EmployeeSector getSector() {
        return sector;
    }

    public void setSector(EmployeeSector sector) {
        this.sector = sector;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
