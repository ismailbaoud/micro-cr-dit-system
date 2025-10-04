package main.java.com.ismail.MicroCreditScoringSystem.service;

import main.java.com.ismail.MicroCreditScoringSystem.Algorithlms.ScoringAlgorithm;
import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ActivitySector;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.EmployeeSector;
import main.java.com.ismail.MicroCreditScoringSystem.repository.PersonRepository;
import org.mariadb.jdbc.client.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class PersonService {
    PersonRepository personRepository = new PersonRepository();
    ScoringAlgorithm sa = new ScoringAlgorithm();

    public void createEmployee(String firstName, String lastName, LocalDate birthday, String city, Boolean investmentBoolean, Boolean placement, String familyStatus, Double salary, Integer seniority, ContractType contractType, EmployeeSector sector, String position, Integer childCount) {
        try {
            Employee employee = new Employee(firstName, lastName, birthday, city, investmentBoolean, placement, familyStatus, salary, seniority, contractType, sector, position, childCount);
            employee.setScore(sa.totalScore(employee));

            if (personRepository.createEmployee(employee)) {
                System.out.println("Employee created successfully !");
            } else {
                System.out.println("Employee has not been created !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createProfessional(String firstName, String lastName, LocalDate birthday, String city, Boolean investmentBoolean, Boolean placement, String familyStatus, Double income, String taxReg, ActivitySector activitySector, String activity, Integer childCount, ContractType contractType, Integer seniority) {
        try {
            Professional professional = new Professional(firstName, lastName, birthday, city, investmentBoolean, placement, familyStatus, activity, activitySector, taxReg, income, childCount, contractType);
            professional.setSeniority(seniority);
            if (personRepository.createProfessional(professional)) {
                System.out.println("Professional has been created successfully !");
            } else {
                System.out.println("professional has not been created !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(UUID id, String firstName, String lastName, LocalDate birthday, String city, Boolean investment, Boolean placement, String familyStatus, Double salary, Integer seniority, ContractType contractType, EmployeeSector sector, String position, Integer childCount) {
        try {
            Object person = personRepository.getPerson(id);

            if (person == null) {
                System.out.println("The client does not exist!");
                return;
            }

            if (!(person instanceof Employee)) {
                System.out.println("This ID does not belong to an Employee!");
                return;
            }

            Employee updated = new Employee(
                    firstName,
                    lastName,
                    birthday,
                    city,
                    investment != null ? investment : false,
                    placement != null ? placement : false,
                    familyStatus,
                    salary != null ? salary : 0.0,
                    seniority != null ? seniority : 0,
                    contractType != null ? contractType : ContractType.Other,
                    sector != null ? sector : EmployeeSector.PUBLIC,
                    position,
                    childCount != null ? childCount : 0
            );

            if (personRepository.updateEmployee(id, updated)) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Failed to update Employee!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProfessional(UUID id, String firstName, String lastName, LocalDate birthday, String city, Boolean investment, Boolean placement, String familyStatus, Double income, String taxReg, ActivitySector activitySector, String activity, Integer childCount, ContractType contractType) {
        try {
            Object person = personRepository.getPerson(id);

            if (person == null) {
                System.out.println("The client does not exist!");
                return;
            }

            if (!(person instanceof Professional)) {
                System.out.println("This ID does not belong to a Professional!");
                return;
            }

            Professional updated = new Professional(
                    firstName,
                    lastName,
                    birthday,
                    city,
                    investment != null ? investment : false,
                    placement != null ? placement : false,
                    familyStatus,
                    activity,
                    activitySector != null ? activitySector : ActivitySector.OTHER,
                    taxReg,
                    income != null ? income : 0.0,
                    childCount != null ? childCount : 0,
                    contractType != null ? contractType : ContractType.Other
            );

            if (personRepository.updateProfessional(id, updated)) {
                System.out.println("Professional updated successfully!");
            } else {
                System.out.println("Failed to update Professional!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(UUID id) {
        try {
            if (personRepository.getPerson(id) != null) {
                if (personRepository.deleteClient(personRepository.getPerson(id))) {
                    System.out.println("the client has been deleted successfully !");
                } else {
                    System.out.println("the client has not been deleted");
                }
            } else {
                System.out.println("This client not exist !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Person getClient(UUID id) {
        try {
            Person person = personRepository.getPerson(id);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Person displayClient(UUID id) {
        try {
            Person person = personRepository.getPerson(id);

            if (person != null) {
                return person;
            }
            System.out.println("the client does not exist !");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Person> displayAllClients() {
        try {
            return personRepository.displayAllClients();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
