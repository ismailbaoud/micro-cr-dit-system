package main.java.com.ismail.MicroCreditScoringSystem.model;

import main.java.com.ismail.MicroCreditScoringSystem.model.enums.Decision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Loan {
    private UUID id;
    private String employee_id;
    private String profrssional_id;
    private LocalDateTime dateDeCredit;
    private double montantDemande;
    private double montantOctroye;
    private double tauxInteret;
    private int dureeEnMois;
    private String typeCredit;
    private Decision decision;

    public ArrayList<Installment> installments = new ArrayList<>();


    public Loan( String employeeId, String profrssionalId, double montantDemande, double montantOctroye,
                double tauxInteret, int dureeEnMois,  Decision decision) {
        this.employee_id = employeeId;
        this.profrssional_id = profrssionalId ;
        this.id = UUID.randomUUID();
        this.dateDeCredit = LocalDateTime.now();
        this.montantDemande = montantDemande;
        this.montantOctroye = montantOctroye;
        this.tauxInteret = tauxInteret;
        this.dureeEnMois = dureeEnMois;
//        this.typeCredit = typeCredit;
        this.decision = decision;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getDateDeCredit() { return dateDeCredit; }
    public void setDateDeCredit(LocalDateTime dateDeCredit) { this.dateDeCredit = dateDeCredit; }

    public double getMontantDemande() { return montantDemande; }
    public void setMontantDemande(double montantDemande) { this.montantDemande = montantDemande; }

    public double getMontantOctroye() { return montantOctroye; }
    public void setMontantOctroye(double montantOctroye) { this.montantOctroye = montantOctroye; }

    public double getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(double tauxInteret) { this.tauxInteret = tauxInteret; }

    public int getDureeEnMois() { return dureeEnMois; }
    public void setDureeEnMois(int dureeEnMois) { this.dureeEnMois = dureeEnMois; }

    public String getTypeCredit() { return typeCredit; }
    public void setTypeCredit(String typeCredit) { this.typeCredit = typeCredit; }

    public Decision getDecision() { return decision; }
    public void setDecision(Decision decision) { this.decision = decision; }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        employee_id = employee_id;
    }

    public String getProfrssional_id() {
        return profrssional_id;
    }

    public void setProfrssional_id(String profrssional_id) {
        profrssional_id = profrssional_id;
    }


    public ArrayList<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(ArrayList<Installment> installments) {
        this.installments = installments;
    }
}
