package main.java.com.ismail.MicroCreditScoringSystem.model;

import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;

import java.time.LocalDate;

public class Incident {
    private LocalDate incidentDate;
    private Integer scoreImpact;
    private PaymentStatus type;

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Integer getScoreImpact() {
        return scoreImpact;
    }

    public void setScoreImpact(Integer scoreImpact) {
        this.scoreImpact = scoreImpact;
    }

    public PaymentStatus getType() {
        return type;
    }

    public void setType(PaymentStatus type) {
        this.type = type;
    }
}
