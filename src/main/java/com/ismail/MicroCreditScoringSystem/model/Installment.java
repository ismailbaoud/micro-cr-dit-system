package main.java.com.ismail.MicroCreditScoringSystem.model;

import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Installment {
    private LocalDate dueDate;
    private Double monthlyPayment;
    private LocalDateTime paymentDate;
    private PaymentStatus status ;
    private UUID id ;
    private UUID loanId ;


    public Installment( Double monthlyPayment, LocalDateTime paymentDate, PaymentStatus status) {
        this.monthlyPayment = monthlyPayment;
        this.paymentDate = paymentDate;
        this.status = status;
        this.id = UUID.randomUUID();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }
}
