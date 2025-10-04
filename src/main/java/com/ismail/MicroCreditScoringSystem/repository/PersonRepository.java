package main.java.com.ismail.MicroCreditScoringSystem.repository;

import main.java.com.ismail.MicroCreditScoringSystem.config.ConnectionDB;
import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ActivitySector;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.EmployeeSector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonRepository {
    Connection conn;

    public PersonRepository() {
        conn = ConnectionDB.getInstance().getConnection();
    }

    public Boolean createEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (id ,first_name, last_name, birth_date, city, investment, placement, children_count, marital_status, score, salary, seniority_in_years, contract_type, sector) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(employee.getId()));
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setObject(4, employee.getBirthday());
            stmt.setString(5, employee.getCity());
            stmt.setBoolean(6, employee.isInvestment());
            stmt.setBoolean(7, employee.isPlacement());
            stmt.setInt(8, employee.getChildrenCount());
            stmt.setString(9, employee.getFamilyStatus());
            stmt.setInt(10, employee.getScore());
            stmt.setDouble(11, employee.getSalary());
            stmt.setInt(12, employee.getSeniority());
            stmt.setObject(13, String.valueOf(employee.getContractType()));
            stmt.setObject(14, String.valueOf(employee.getSector()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("you have sql err");
            ;
        } catch (Exception e) {
            System.out.println("you have err");
        }
        return false;
    }

    public Boolean createProfessional(Professional professional) {
        String sql = "INSERT INTO Professional (" +
                "id," +
                "first_name, " +
                "last_name, " +
                "birth_date, " +
                "city, " +
                "investment," +
                "placement," +
                "children_count, " +
                "marital_status, " +
                "score, " +
                "income, " +
                "seniority_in_years, " +
                "tax_registration, " +
                "contract_type, " +
                "activity, " +
                "activity_sector" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, professional.getId());
            stmt.setString(2, professional.getFirstName());
            stmt.setString(3, professional.getLastName());
            stmt.setObject(4, professional.getBirthday());
            stmt.setString(5, professional.getCity());
            stmt.setBoolean(6, professional.isInvestment());
            stmt.setBoolean(7, professional.isPlacement());
            stmt.setInt(8, professional.getChildrenCount());
            stmt.setString(9, professional.getFamilyStatus());
            stmt.setDouble(10, professional.getScore());
            stmt.setDouble(11, professional.getIncome());
            stmt.setInt(12, professional.getSeniority());
            stmt.setString(13, professional.getTaxRegistration());
            stmt.setObject(14, String.valueOf(professional.getContractType()));
            stmt.setString(15, professional.getActivity());
            stmt.setObject(16, String.valueOf(professional.getActivitySector()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteClient(Person person) {
        String sql;
        if (person instanceof Employee) {
            sql = "DELETE from Employee where id = ?";
        } else {
            sql = "DELETE from Professional where id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, person.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("you have sql err : " + e.getMessage());
        }
        return false;
    }

    public Person getPerson(UUID id) {
        Person person = findEmployee(id);
        if (person != null) return person;

        return findProfessional(id);
    }

    private Employee findEmployee(UUID id) {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate(),
                            rs.getString("city"),
                            true,
                            true,
                            rs.getString("marital_status"),
                            rs.getDouble("salary"),
                            rs.getInt("seniority_in_years"),
                            ContractType.valueOf(rs.getString("contract_type")),
                            EmployeeSector.valueOf(rs.getString("sector")),
                            "position",
                            rs.getInt("children_count")
                    );
                    emp.setId(UUID.fromString(rs.getString("id")));
                    emp.setScore(rs.getInt("score"));
                    return emp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Professional findProfessional(UUID id) {
        String sql = "SELECT * FROM Professional WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professional pro = new Professional(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate(),
                            rs.getString("city"),
                            true,
                            true,
                            rs.getString("marital_status"),
                            "activity",
                            ActivitySector.valueOf(rs.getString("activity_sector")),
                            rs.getString("tax_registration"),
                            rs.getDouble("income"),
                            rs.getInt("children_count"),
                            ContractType.valueOf(rs.getString("contract_type"))
                    );
                    pro.setId(UUID.fromString(rs.getString("id")));
                    pro.setScore(rs.getInt("score"));
                    return pro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployee(UUID id, Employee employee) {
        String sql = "UPDATE Employee SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "birth_date = ?, " +
                "city = ?, " +
                "marital_status = ?, " +
                "salary = ?, " +
                "seniority_in_years = ?, " +
                "contract_type = ?, " +
                "sector = ?, " +
                "children_count = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setObject(3, employee.getBirthday().atStartOfDay().toLocalDate());
            stmt.setString(4, employee.getCity());
            stmt.setString(5, employee.getFamilyStatus());
            stmt.setDouble(6, employee.getSalary());
            stmt.setInt(7, employee.getSeniority());
            stmt.setString(8, employee.getContractType().name());
            stmt.setString(9, employee.getSector().name());
            stmt.setInt(10, employee.getChildrenCount());
            stmt.setObject(11, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfessional(UUID id, Professional professional) {
        String sql = "UPDATE Professional SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "birth_date = ?, " +
                "city = ?, " +
                "marital_status = ?, " +
                "income = ?, " +
                "tax_registration = ?, " +
                "activity_sector = ?, " +
                "children_count = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professional.getFirstName());
            stmt.setString(2, professional.getLastName());
            stmt.setObject(3, professional.getBirthday().atStartOfDay().toLocalDate());
            stmt.setString(4, professional.getCity());
            stmt.setString(5, professional.getFamilyStatus());
            stmt.setDouble(6, professional.getIncome());
            stmt.setString(7, professional.getTaxRegistration());
            stmt.setString(8, professional.getActivitySector().name());
            stmt.setInt(9, professional.getChildrenCount());
            stmt.setObject(10, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Person> displayAllClients() {
        ArrayList<Person> clients = new ArrayList<>();

        String empSql = "SELECT * FROM Employee";
        try (PreparedStatement stmt = conn.prepareStatement(empSql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getObject("birth_date", LocalDate.class),
                        rs.getString("city"),
                        rs.getBoolean("investment"),
                        rs.getBoolean("placement"),
                        rs.getString("marital_status"),
                        rs.getDouble("salary"),
                        rs.getInt("seniority_in_years"),
                        ContractType.valueOf(rs.getString("contract_type")),
                        EmployeeSector.valueOf(rs.getString("sector")),
                        "position",
                        rs.getInt("children_count")
                );
                emp.setId(UUID.fromString(rs.getString("id")));

                clients.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ----------- Professionals -----------
        String proSql = "SELECT * FROM Professional";
        try (PreparedStatement stmt = conn.prepareStatement(proSql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professional pro = new Professional(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getObject("birth_date", LocalDate.class),
                        rs.getString("city"),
                        rs.getBoolean("investment"),
                        rs.getBoolean("placement"),
                        rs.getString("marital_status"),
                        rs.getString("activity"),
                        ActivitySector.valueOf(rs.getString("activity_sector")),
                        rs.getString("tax_registration"),
                        rs.getDouble("income"),
                        rs.getInt("children_count"),
                        ContractType.valueOf(rs.getString("contract_type"))
                );
                pro.setId(UUID.fromString(rs.getString("id")));
                clients.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public Boolean updateScoring(Person client, Integer score, String operation) {
        if (client instanceof Employee) {
            Integer finalScore;
            if(operation.equals("addition")){
                finalScore = Math.max(client.getScore() + score, 0);
            }else {
                finalScore = Math.max(client.getScore() - score, 0);
            }
            String empSql = "UPDATE Employee set score = ? where id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(empSql)) {
                stmt.setInt(1, finalScore);
                stmt.setObject(2, client.getId());
                return stmt.executeUpdate() >0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {

            String proSql = "UPDATE Professional set score = ? where id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(proSql)) {
                stmt.setInt(1, (client.getScore()+score));
                stmt.setObject(2, client.getId());
                return stmt.executeUpdate() >0;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public List<Person> getAllClients() {
        List<Person> clients = new ArrayList<>();

        String empSql = "SELECT * FROM Employee";
        try (PreparedStatement stmt = conn.prepareStatement(empSql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("city"),
                        rs.getBoolean("investment"),
                        rs.getBoolean("placement"),
                        rs.getString("marital_status"),
                        rs.getDouble("salary"),
                        rs.getInt("seniority_in_years"),
                        ContractType.valueOf(rs.getString("contract_type")),
                        EmployeeSector.valueOf(rs.getString("sector")),
                        "position",
                        rs.getInt("children_count")
                );
                emp.setId(UUID.fromString(rs.getString("id")));
                emp.setScore(rs.getInt("score"));
                clients.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String proSql = "SELECT * FROM Professional";
        try (PreparedStatement stmt = conn.prepareStatement(proSql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Professional pro = new Professional(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("city"),
                        rs.getBoolean("investment"),
                        rs.getBoolean("placement"),
                        rs.getString("marital_status"),
                        rs.getString("activity"),
                        ActivitySector.valueOf(rs.getString("activity_sector")),
                        rs.getString("tax_registration"),
                        rs.getDouble("income"),
                        rs.getInt("children_count"),
                        ContractType.valueOf(rs.getString("contract_type"))
                );
                pro.setId(UUID.fromString(rs.getString("id")));
                pro.setScore(rs.getInt("score"));
                clients.add(pro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }



}