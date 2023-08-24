package org.Business;

import org.Business.customer.CustomerManagement;
import org.Business.employee.Employee;
import org.Business.employee.EmployeeAccount;
import org.Business.order.OrderManagement;
import org.Business.product.ProductManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusinessManagement {
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<EmployeeAccount> employeeAccounts = new ArrayList<>();

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



    public static EmployeeAccount logIn() {
        boolean result;
        EmployeeAccount employeeAccount;

        for (int i = 0; i < 3; i++) {
            System.out.print("LOGIN: ");
            String login = userInput.nextLine();

            System.out.print("PASSWORD: ");
            String password = userInput.nextLine();
            employeeAccount = new EmployeeAccount(login, password);
            result = doesAccountExist(employeeAccount);
            if (result) {
                return employeeAccount;
            }
        }

        return null;
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
                logout();
                break;
            case 7:
                exit();
        }
    }

    private static void logout() {
        if(logIn() != null) homeMenu();
    }

    private static void exit() {
        System.out.println("\nThank you for using Business Management! :-)");
        System.exit(0);
    }

    private static void adminMenu() {
        showAdminMenuInterface();
        try(Scanner userInput = new Scanner(System.in)) {
            switch (userInput.nextInt()) {
                case 4:
                    homeMenu();
            }
        }
    }


    private static void myAccountMenu() {
        showMyAccountMenuInterface();
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
