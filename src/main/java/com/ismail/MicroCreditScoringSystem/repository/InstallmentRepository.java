package main.java.com.ismail.MicroCreditScoringSystem.repository;

import main.java.com.ismail.MicroCreditScoringSystem.config.ConnectionDB;
import main.java.com.ismail.MicroCreditScoringSystem.model.Installment;
import main.java.com.ismail.MicroCreditScoringSystem.model.Loan;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.PaymentStatus;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.sql.Date;
public class InstallmentRepository {
    Connection conn ;
    public InstallmentRepository() {
        conn = ConnectionDB.getInstance().getConnection();
    }


    public boolean createInstallments(Installment installment ,Loan loan, Integer durationInMonths) {
        String sql = "INSERT INTO Installment (id, loan_id, due_date, amount, payment_date) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            LocalDate dueDate = LocalDate.now();

            for (int i = 1; i <= durationInMonths; i++) {
                installment.setId(UUID.randomUUID());
                installment.setDueDate(dueDate);
                installment.setPaymentDate(null);

                stmt.setObject(1, installment.getId());
                stmt.setObject(2, loan.getId());
                stmt.setObject(3, java.sql.Date.valueOf(installment.getDueDate()));
                stmt.setObject(4, installment.getMonthlyPayment());
                stmt.setObject(5, installment.getPaymentDate());

                stmt.addBatch();

                dueDate = dueDate.plusMonths(1);
            }

            int[] results = stmt.executeBatch();
            return Arrays.stream(results).allMatch(r -> r > 0);

        } catch (SQLException e) {
            System.out.println("Error inserting installments: " + e.getMessage());
            return false;
        }
    }

    public boolean updateInstallmentStatus(UUID installmentId, PaymentStatus status) {
        String sql = "UPDATE Installment SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (status == null) {
                stmt.setNull(1, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(1, status.name());
            }

            stmt.setString(2, installmentId.toString());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating installment status: " + e.getMessage());
            return false;
        }
    }


    public ArrayList<Installment> getInstallments() {
        ArrayList<Installment> installments = new ArrayList<>();
        String sql = "SELECT * FROM Installment";
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
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
                installment.setLoanId(UUID.fromString(rs.getString("loan_id")));
                installment.setId(installmentId);

                installments.add(installment);
            }

        }catch (SQLException e) {
            System.out.println("sql Exception :" +  e.getMessage());
        }catch (Exception e) {
            System.out.println("Exception : " +e.getMessage());
        }
        return installments;
    }


    public boolean payInstallment(UUID installmentId) {
        String sql = "UPDATE Installment set payment_date = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setObject(1, LocalDate.now());
            stmt.setObject(2, installmentId);
            return stmt.executeUpdate() > 0;
        }catch (SQLException e) {
            System.out.println("sql Exception : "+ e.getMessage());
        }
        return false;
    }


}
