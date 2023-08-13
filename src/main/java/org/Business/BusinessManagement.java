package org.Business;

import org.Business.employee.Employee;
import org.Business.employee.EmployeeLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusinessManagement {
    public static final Scanner userInput = new Scanner(System.in);
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<EmployeeLogin> employeeLogins = new ArrayList<>();

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

    public static void createEmployeeLogin(String login, String password, Long employeeID) {
        EmployeeLogin employeeLogin = new EmployeeLogin(login, password, employeeID);
        if(!employeeLogins.contains(employeeLogin))
            employeeLogins.add(employeeLogin);
        else
            System.out.println("EmployeeLogin exists. You can only change login or password.");
    }


    public static boolean logIn() {
        boolean result = false;
        do {
            System.out.print("LOGIN: ");
            String login = userInput.nextLine();

            System.out.print("PASSWORD: ");
            String password = userInput.nextLine();
            result =  doesLoginExist(login, password);
        } while(!result);
        return result;
    }

    public static int numberOfEmployees() {
        return employees.size();
    }

    public static void homeMenuController() {
        showHomeMenuInterface();
        switch(userInput.nextInt()) {
            case 1:
                ordersMenu();
                break;
            case 2:
                customersMenu();
                break;
            case 3:

        }
    }

    private static void customersMenu() {
        showCustomersMenuInterface();
    }

    private static void showCustomersMenuInterface() {
        System.out.println("=".repeat(9));
        System.out.println("1. Show all customers");
        System.out.println("2. Find customer");
        System.out.println("3. Add customer");
        System.out.println("4. Delete customer");
        System.out.println("5. Go back to home menu");
    }

    private static void ordersMenu() {
        showOrdersMenuInterface();
    }

    private static void showOrdersMenuInterface() {
        System.out.println("=".repeat(9));
        System.out.println("1. Create an order");
        System.out.println("2. Delete an order");
        System.out.println("3. Show orders");
        System.out.println("4. Go back to home menu");
        System.out.println("=".repeat(9) + "\n\n");
    }

    private static void showHomeMenuInterface() {
        System.out.println("=".repeat(9));
        System.out.println("1. Orders");
        System.out.println("2. Customers");
        System.out.println("3. My account");
        System.out.println("4. Logout");
        System.out.println("=".repeat(9) + "\n\n");
    }


    private static boolean doesLoginExist(String login, String password) {
        EmployeeLogin employeeLogin = new EmployeeLogin(login, password);

        boolean result = employeeLogins.stream()
                .anyMatch(e -> e.equals(employeeLogin));
        if(result) {
            System.out.println("\nHello! You successfully logged in!\n");
        } else {
            System.out.println("\nLogin or password is incorrect. Try again!\n");
        }
        return result;
    }


}
