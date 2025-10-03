package main.java.com.ismail.MicroCreditScoringSystem.controller;

import main.java.com.ismail.MicroCreditScoringSystem.model.Person;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ActivitySector;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.ContractType;
import main.java.com.ismail.MicroCreditScoringSystem.model.enums.EmployeeSector;
import main.java.com.ismail.MicroCreditScoringSystem.service.PersonService;
import main.java.com.ismail.MicroCreditScoringSystem.model.Employee;
import main.java.com.ismail.MicroCreditScoringSystem.model.Professional;
import main.java.com.ismail.MicroCreditScoringSystem.view.ClientView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class PersonController {
    private final Scanner scanner = new Scanner(System.in);
    private final PersonService personService = new PersonService();
    private final ClientView clientView = new ClientView();

    public PersonController(Integer choice) {
        manageOptions(choice);
    }

    public void manageOptions(Integer choice) {
        switch (choice) {
            case 1:
                createClient();
                break;
            case 2:
                updateClient();
                break;
            case 3:
                deleteClient();
                break;
            case 4:
                displayClientDetails();
                break;
            case 5:
                displayAllClients();
                break;
        }
    }

    public void createClient() {
        int type = choosePersonType();
        CommonPersonData common = readCommonPersonData();

        if (type == 1) {
            EmployeeData employeeData = readEmployeeData();
            personService.createEmployee(
                    common.firstName, common.lastName, common.birthday, common.city,
                    common.investment, common.placement, common.familyStatus,
                    employeeData.salary, employeeData.seniority,
                    employeeData.contractType, employeeData.sector,
                    employeeData.position, common.childrenCount
            );
        } else {
            ProfessionalData professionalData = readProfessionalData();
            personService.createProfessional(
                    common.firstName, common.lastName, common.birthday, common.city,
                    common.investment, common.placement, common.familyStatus,
                    professionalData.income, professionalData.taxRegistration,
                    professionalData.activitySector, professionalData.activity,
                    common.childrenCount, professionalData.contractType, professionalData.seniority
            );
        }
    }

    public void updateClient() {
        System.out.println("Please enter the client ID you want to update: ");
        String idStr = scanner.next();
        UUID id = UUID.fromString(idStr);
        Person person = personService.getClient(id);

        if (person == null) {
            System.out.println("No client found with ID: " + id);
            return;
        }

        if (person instanceof Employee) {
            Employee emp = (Employee) person;
            String firstName = askForUpdate("First Name", emp.getFirstName());
            String lastName = askForUpdate("Last Name", emp.getLastName());
            String city = askForUpdate("City", emp.getCity());
            double salary = askForUpdateDouble("Salary", emp.getSalary());
            int seniority = askForUpdateInt("Seniority", emp.getSeniority());
            String position = askForUpdate("Position", emp.getPosition());

            personService.updateEmployee(
                    emp.getId(), firstName, lastName, emp.getBirthday(), city,
                    emp.isInvestment(), emp.isPlacement(), emp.getFamilyStatus(),
                    salary, seniority, emp.getContractType(), emp.getSector(),
                    position, emp.getChildrenCount()
            );

        } else if (person instanceof Professional) {
            Professional pro = (Professional) person;
            String firstName = askForUpdate("First Name", pro.getFirstName());
            String lastName = askForUpdate("Last Name", pro.getLastName());
            String city = askForUpdate("City", pro.getCity());
            double income = askForUpdateDouble("Income", pro.getIncome());
            String taxReg = askForUpdate("Tax Registration", pro.getTaxRegistration());
            String activity = askForUpdate("Activity", pro.getActivity());
            ContractType contractType = chooseContractType();

            personService.updateProfessional(
                    pro.getId(), firstName, lastName, pro.getBirthday(), city,
                    pro.isInvestment(), pro.isPlacement(), pro.getFamilyStatus(),
                    income, taxReg, pro.getActivitySector(), activity,
                    pro.getChildrenCount(), contractType
            );
        }
    }

    public void deleteClient() {
        System.out.println("Please enter the client ID that you want to delete : ");
        String idStr = scanner.nextLine();
        UUID id = UUID.fromString(idStr);
        personService.deleteClient(id);
    }

    public void displayClientDetails() {
        System.out.println("Please enter the client id : ");
        UUID id = UUID.fromString(scanner.next());
        Person person = personService.displayClient(id);
        if(person != null) {
            if(person instanceof Employee) {
                clientView.displayEmployee(person);
            } else {
                clientView.displayProfessional(person);
            }
        }
    }

    public void displayAllClients() {
        ArrayList<Person> persons = personService.displayAllClients();
        if(persons != null && !persons.isEmpty()) {
            clientView.displayAllClients(persons);
        } else {
            System.out.println("There are no clients.");
        }
    }

    private int choosePersonType() {
        int type;
        do {
            System.out.println("Types : ");
            System.out.println("1 => Employee");
            System.out.println("2 => Professional");
            System.out.print("Please enter your type : ");
            type = scanner.nextInt();
        } while (type != 1 && type != 2);
        return type;
    }

    private CommonPersonData readCommonPersonData() {
        System.out.print("Please enter your first name : ");
        String firstName = scanner.next();
        System.out.print("Please enter your last name : ");
        String lastName = scanner.next();
        System.out.print("Please enter your birthday (YYYY-MM-DD): ");
        LocalDate birthday = LocalDate.parse(scanner.next());
        System.out.print("Please enter your city : ");
        String city = scanner.next();

        boolean investment = askYesNo("Do you have investment?");
        boolean placement = askYesNo("Do you have placement?");

        System.out.print("Please enter number of children : ");
        int childrenCount = scanner.nextInt();
        System.out.print("Please enter your family status : ");
        String familyStatus = scanner.next();

        return new CommonPersonData(firstName, lastName, birthday, city, investment, placement, familyStatus, childrenCount);
    }

    private EmployeeData readEmployeeData() {
        System.out.print("Please enter your salary : ");
        double salary = scanner.nextDouble();
        System.out.print("Please enter your seniority (Years) : ");
        int seniority = scanner.nextInt();
        EmployeeSector sector = chooseEmployeeSector();
        System.out.print("Please enter your position : ");
        String position = scanner.next();
        ContractType contractType = chooseContractType();
        return new EmployeeData(salary, seniority, contractType, sector, position);
    }

    private ProfessionalData readProfessionalData() {
        System.out.print("Enter income: ");
        double income = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter tax registration number: ");
        String taxReg = scanner.nextLine();

        System.out.print("Enter your seniority : ");
        Integer seniority = scanner.nextInt();

        ActivitySector activitySector = chooseActivitySector();

        System.out.print("Enter specific activity (e.g., Lawyer, Mechanic): ");
        String activity = scanner.nextLine();

        ContractType contractType = chooseContractType();

        return new ProfessionalData(income, taxReg, activitySector, activity, contractType, seniority);
    }

    private boolean askYesNo(String message) {
        System.out.println(message);
        System.out.println("1 => true");
        System.out.println("2 => false");
        System.out.print("Please enter your choice : ");
        int choice = scanner.nextInt();
        return choice == 1;
    }

    private ContractType chooseContractType() {
        System.out.println("Enter contract type: ");
        System.out.println("1 => CDI");
        System.out.println("2 => CDD");
        System.out.println("3 => STABLE_LIBERAL_PROFESSION");
        System.out.println("4 => SELF_EMPLOYED");
        System.out.println("5 => OTHER");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return ContractType.CDI;
            case 2:
                return ContractType.CDD;
            case 3:
                return ContractType.STABLE_LIBERAL_PROFESSION;
            case 4:
                return ContractType.SELF_EMPLOYED;
            default:
                return ContractType.Other;
        }
    }

    private EmployeeSector chooseEmployeeSector() {
        System.out.println("Enter sector : ");
        System.out.println("1 => PUBLIC ");
        System.out.println("2 => LARGE_COMPANY ");
        System.out.println("3 => SME ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return EmployeeSector.PUBLIC;
            case 2:
                return EmployeeSector.LARGE_COMPANY;
            case 3:
                return EmployeeSector.SME;
            default:
                return EmployeeSector.PUBLIC;
        }
    }

    private ActivitySector chooseActivitySector() {
        System.out.println("Select activity sector:");
        System.out.println("1 => AGRICULTURE");
        System.out.println("2 => SERVICE");
        System.out.println("3 => COMMERCE");
        System.out.println("4 => CONSTRUCTION");
        System.out.println("5 => OTHER");
        String choiceStr = scanner.nextLine().trim();
        switch (choiceStr) {
            case "1":
                return ActivitySector.AGRICULTURE;
            case "2":
                return ActivitySector.SERVICE;
            case "3":
                return ActivitySector.INDUSTRY;
            case "4":
                return ActivitySector.CONSTRUCTION;
            default:
                return ActivitySector.OTHER;
        }
    }

    private String askForUpdate(String field, String currentValue) {
        scanner.nextLine();
        System.out.print(field + " [" + currentValue + "]: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }

    private double askForUpdateDouble(String field, double currentValue) {
        System.out.print(field + " [" + currentValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return currentValue;
        try { return Double.parseDouble(input); } catch (NumberFormatException e) { return currentValue; }
    }

    private int askForUpdateInt(String field, int currentValue) {
        System.out.print(field + " [" + currentValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return currentValue;
        try { return Integer.parseInt(input); } catch (NumberFormatException e) { return currentValue; }
    }

    private static class CommonPersonData {
        String firstName, lastName, city, familyStatus;
        LocalDate birthday;
        boolean investment, placement;
        int childrenCount;

        CommonPersonData(String firstName, String lastName, LocalDate birthday,
                         String city, boolean investment, boolean placement,
                         String familyStatus, int childrenCount) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.city = city;
            this.investment = investment;
            this.placement = placement;
            this.familyStatus = familyStatus;
            this.childrenCount = childrenCount;
        }
    }

    private static class EmployeeData {
        double salary;
        int seniority;
        ContractType contractType;
        EmployeeSector sector;
        String position;

        EmployeeData(double salary, int seniority, ContractType contractType,
                     EmployeeSector sector, String position) {
            this.salary = salary;
            this.seniority = seniority;
            this.contractType = contractType;
            this.sector = sector;
            this.position = position;
        }
    }

    private static class ProfessionalData {
        double income;
        String taxRegistration;
        ActivitySector activitySector;
        String activity;
        ContractType contractType;
        Integer seniority;

        ProfessionalData(double income, String taxRegistration,
                         ActivitySector activitySector, String activity, ContractType contractType, Integer seniority) {
            this.income = income;
            this.taxRegistration = taxRegistration;
            this.activitySector = activitySector;
            this.activity = activity;
            this.contractType = contractType;
            this.seniority = seniority;
        }
    }
}
