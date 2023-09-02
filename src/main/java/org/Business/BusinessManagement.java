package org.Business;

import org.Business.customer.CustomerManagement;
import org.Business.employee.Employee;
import org.Business.employee.EmployeeAccount;
import org.Business.employee.EmployeeProfession;
import org.Business.order.OrderManagement;
import org.Business.product.ProductManagement;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

public class BusinessManagement {
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<EmployeeAccount> employeeAccounts = new ArrayList<>();

    private static long thisEmployeeID;
    private static boolean adminFlag;

    public static final Scanner userInput = new Scanner(System.in);


    private BusinessManagement() {}


    public static void saveEmployee(Employee employee) {
        if(employees.contains(employee)) {
            System.out.println("This Employee actually exists in List.");
            return;
        }
        employees.add(employee);
    }

    public static void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }

    public static void deleteEmployee(int ID) {
        employees.removeIf(employee -> employee.getID() == ID);
    }

    public static boolean addEmployee(Employee employee) {
        if(employees.contains(employee)) {
            System.out.println("\nThat employee exists in database.\n");
            return false;
        }
        employees.add(employee);
        return true;
    }

    public static void addEmployeeAccount(String login, String password, Long employeeID) {
        EmployeeAccount employeeAccount = new EmployeeAccount(login, password, employeeID);
        if(!employeeAccounts.contains(employeeAccount))
            employeeAccounts.add(employeeAccount);
        else
            System.out.println("EmployeeAccount exists. You can only change login or password.");
    }



    public static void logIn() {
        EmployeeAccount employeeAccount;

        while(true) {
            System.out.print("LOGIN: ");
            String login = userInput.nextLine();

            System.out.print("PASSWORD: ");
            String password = userInput.nextLine();
            employeeAccount = new EmployeeAccount(login, password);
            if(doesAccountExist(employeeAccount)) {
                setThisEmployeeID(employeeAccount);
                setAdminFlagIfPresent();
                break;
            }
        }
        homeMenu();
    }


    private static void setThisEmployeeID(EmployeeAccount employeeAccount) {
        OptionalLong employeeID = employeeAccounts.stream()
                .filter(e -> e.equals(employeeAccount))
                .mapToLong(EmployeeAccount::getEmployeeID)
                .findFirst();
        if(employeeID.isPresent())
            thisEmployeeID = employeeID.getAsLong();
    }

    private static void setAdminFlagIfPresent() {
        boolean isEmployeeAdmin = employees.stream()
                .filter(e -> e.getID() == thisEmployeeID)
                .anyMatch(e -> e.getProfession().equals(EmployeeProfession.ADMINISTRATOR));

        if(isEmployeeAdmin) {
            adminFlag = true;
        }

    }

    public static int numberOfEmployees() {
        return employees.size();
    }

    public static void homeMenu() {
        showHomeMenuInterface();

        int result = userInput.nextInt();
        userInput.nextLine();
        switch (result) {
            case 1:
                OrderManagement.ordersMenu();
                break;
            case 2:
                CustomerManagement.customersMenu();
                break;
            case 3:
                ProductManagement.productsMenu();
                break;
            case 4:
                myAccountMenu();
                break;
            case 5:
                adminMenu();
                break;
            case 6:
                logIn();
                break;
            case 7:
                exit();
        }
    }



    private static void exit() {
        System.out.println("\nThank you for using Business Management! :-)");
        System.exit(0);
    }

    private static void adminMenu() {
        if(!isAdmin()) {
            System.out.println("You don't have access to admin menu!");
            homeMenu();
        }
        showAdminMenuInterface();
        int resultInput = userInput.nextInt();
        userInput.nextLine();
        switch (resultInput) {
            case 4:
                homeMenu();
        }
    }
    
    private static boolean isAdmin() {
        return adminFlag;
    }

    private static void myAccountMenu() {
        showMyAccountMenuInterface();

        int resultInput = userInput.nextInt();
        userInput.nextLine();
        switch (resultInput) {
            case 1:
                myAccountInformation();
                break;
            case 2:
                editEmployeeInformation();
                break;
            case 3:
                homeMenu();
        }


    }

    private static void editEmployeeInformation() {
        System.out.println("Do you want change: ");
        System.out.println("1. Login\t2. Password");
        System.out.println("3. Street with number\t4. Postal code\t5. City\t6. Country");
        System.out.println("7. Profession\t8. Salary");


        int inputResult = userInput.nextInt();
        userInput.nextLine();
        switch (inputResult) {
            case 1:
                String login;
                do {
                    System.out.print("Type new login: ");
                    login = userInput.nextLine();
                } while (isLoginAvailable(login));
                String finalLogin = login;
                editEmployeeAccountInformationByData(e -> e.setLogin(finalLogin));
                break;
            case 2:
                System.out.print("Type new password: ");
                editEmployeeAccountInformationByData(e -> e.setPassword(userInput.nextLine()));
                break;
            case 3:
                System.out.print("Type new street with number: ");
                editEmployeeInformationByData(e -> e.getAddress().setStreetWithNumber(userInput.nextLine()));
                break;
            case 4:
                System.out.print("Type new postal code: ");
                editEmployeeInformationByData(e -> e.getAddress().setPostalCode(userInput.nextLine()));
                break;
            case 5:
                System.out.print("Type new city: ");
                editEmployeeInformationByData(e -> e.getAddress().setCity(userInput.nextLine()));
                break;
            case 6:
                System.out.println("Type new country: ");
                editEmployeeInformationByData(e -> e.getAddress().setCountry(userInput.nextLine()));
                break;
            case 7:
                System.out.println("Choose new profession: ");
                EmployeeProfession newEmployeeProfession = chooseNewProfession();
                editEmployeeInformationByData(e -> e.setProfession(newEmployeeProfession));
                break;
            case 8:
                System.out.println("Type new salary: ");
                editEmployeeInformationByData(e -> e.setSalary(new BigDecimal(userInput.nextLine())));

        }
    }

    private static EmployeeProfession chooseNewProfession() {
        EmployeeProfession[] employeeProfessions = EmployeeProfession.values();
        for(int i = 0; i < employeeProfessions.length; i++) {
            System.out.println(i + ". " + employeeProfessions[i]);
        }
        int inputResult = userInput.nextInt();
        userInput.nextLine();

        return employeeProfessions[inputResult];

    }

    private static void editEmployeeInformationByData(Consumer<Employee> setterExtractor) {
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getID() == thisEmployeeID)
                .findFirst();
        employee.ifPresent(setterExtractor);
        myAccountMenu();
    }

    private static void editEmployeeAccountInformationByData(Consumer<EmployeeAccount> setterExtractor) {
        Optional<EmployeeAccount> employeeAccount = employeeAccounts.stream()
                .filter(e -> e.getEmployeeID() == thisEmployeeID)
                .findFirst();
        employeeAccount.ifPresent(setterExtractor);
        myAccountMenu();
    }

    private static boolean isLoginAvailable(String login) {
        return employeeAccounts.stream()
                .noneMatch(e -> e.getLogin().equals(login));
    }

    private static void myAccountInformation() {
        employeeAccounts.stream()
                .filter(e -> e.getEmployeeID() == thisEmployeeID)
                .forEach(e -> {
                    System.out.println("Employee Account Information");
                    System.out.println("*".repeat(30));
                    System.out.println(e);
                    System.out.println("*".repeat(30).concat("\n"));
                });

        employees.stream()
                .filter(e -> e.getID() == thisEmployeeID)
                .forEach(e -> {
                    System.out.println("Employee Information");
                    System.out.println("*".repeat(30));
                    System.out.println(e);
                    System.out.println("*".repeat(30));
                });

        myAccountMenu();
    }

    private static void showHomeMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***HOME MENU***");
        System.out.println("1. Orders");
        System.out.println("2. Customers");
        System.out.println("3. Products");
        System.out.println("4. My account");
        System.out.println("5. Administrator menu");
        System.out.println("6. Logout");
        System.out.println("7. Exit");
        System.out.println("=".repeat(30) + "\n\n");
    }

    private static void showMyAccountMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***MY ACCOUNT***");
        System.out.println("1. Show information about my account");
        System.out.println("2. Change information about my account");
        System.out.println("3. Go back to home menu");
        System.out.println("=".repeat(30) + "\n\n");
    }

    private static void showAdminMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***ADMIN MENU***");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("3. ");
        System.out.println("4. Go back to home menu");
        System.out.println("=".repeat(30));
    }



    private static boolean doesAccountExist(EmployeeAccount employeeAccount) {
        boolean result =  employeeAccounts.stream()
                .anyMatch(e -> e.equals(employeeAccount));

        if(result) {
            System.out.println("\nHello! You successfully logged in!\n");
        } else {
            System.out.println("\nLogin or password are incorrect. Try again!\n");
        }
        return result;
    }


}
