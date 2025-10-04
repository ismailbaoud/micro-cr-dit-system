package main.java.com.ismail.MicroCreditScoringSystem.repository;

import main.java.com.ismail.MicroCreditScoringSystem.config.ConnectionDB;
import main.java.com.ismail.MicroCreditScoringSystem.model.Installment;
import main.java.com.ismail.MicroCreditScoringSystem.model.Loan;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.Decision;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class LoanRepository {
    private Connection conn;

    public LoanRepository() {
        conn = ConnectionDB.getInstance().getConnection();
    }

    public boolean createLoan(Loan loan) {
        String sql = "INSERT INTO Loan (id, employee_id, professional_id, application_date, requested_amount, amount_granted, duration_in_months, decision) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loan.getId().toString());
            stmt.setString(2, loan.getEmployee_id());
            stmt.setString(3, loan.getProfrssional_id());
            stmt.setObject(4, loan.getDateDeCredit());
            stmt.setDouble(5, loan.getMontantDemande());
            stmt.setDouble(6, loan.getMontantOctroye());
            stmt.setInt(7, loan.getDureeEnMois());
            stmt.setString(8, loan.getDecision().name());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Loan> getLoans() {
        String sql = "SELECT * FROM Loan";
        ArrayList<Loan> loans = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("employee_id"),
                            rs.getString("professional_id"),
                            rs.getDouble("requested_amount"),
                            rs.getDouble("amount_granted"),
                            rs.getDouble("interest_rate"),
                            rs.getInt("duration_in_months"),
                            Decision.valueOf(rs.getString("decision"))
                    );
                    loan.setId(UUID.fromString(rs.getString("id")));
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }


    public ArrayList<Loan> getLoansWithInstallments() {
        String sql = "SELECT l.id AS loan_id, l.*, " +
                "i.id AS installment_id, i.* " +
                "FROM Loan l JOIN Installment i ON l.id = i.loan_id";

        Map<UUID, Loan> loanMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID loanId = UUID.fromString(rs.getString("loan_id"));

                Loan loan = loanMap.get(loanId);
                if (loan == null) {
                    loan = new Loan(
                            rs.getString("employee_id"),
                            rs.getString("professional_id"),
                            rs.getDouble("requested_amount"),
                            rs.getDouble("amount_granted"),
                            rs.getDouble("interest_rate"),
                            rs.getInt("duration_in_months"),
                            Decision.valueOf(rs.getString("decision"))
                    );
                    loan.setId(loanId);
                    loan.setInstallments(new ArrayList<>());
                    loanMap.put(loanId, loan);
                }

                UUID installmentId = UUID.fromString(rs.getString("installment_id"));
                Date sqlDate = rs.getDate("payment_date");
                LocalDateTime dueDate = sqlDate != null ? sqlDate.toLocalDate().atStartOfDay() : null;

                String statusStr = rs.getString("status");
                PaymentStatus status = statusStr == null ? PaymentStatus.NON : PaymentStatus.valueOf(statusStr);
                Installment installment = new Installment(
                        rs.getDouble("amount"),
                        dueDate,
                        status
                );
                installment.setDueDate(LocalDate.parse(rs.getString("due_date")));
                installment.setId(installmentId);

                loan.getInstallments().add(installment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(loanMap.values());
    }


    public ArrayList<Loan> getLoansBystatus() {
        String sql = "select * from Loan";
        ArrayList<Loan> loans = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("employee_id"),
                            rs.getString("professional_id"),
                            rs.getDouble("requested_amount"),
                            rs.getDouble("amount_granted"),
                            rs.getDouble("interest_rate"),
                            rs.getInt("duration_in_months"),
                            Decision.valueOf(rs.getString("decision"))
                    );
                    loan.setId(UUID.fromString(rs.getString("id")));
                    loan.setInstallments(new ArrayList<>());
                loans.add( loan);
                }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return loans;
    }

    public boolean changeStatusManual(UUID id , Decision newDecision) {
        String sql = "update Loan set decision = ? where id = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, newDecision.name());
            stmt.setObject(2, id);
            return stmt.executeUpdate() > 0 ;
        }catch (SQLException e) {
            System.out.println("sql exeption : "+ e.getMessage());
        }
        return false;
    }



}
