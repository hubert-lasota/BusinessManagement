package org.Business;

import org.Business.customer.Customer;
import org.Business.employee.Employee;
import org.Business.employee.EmployeeLogin;
import org.Business.order.Order;
import org.Business.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusinessManagement {
    private static final Scanner userInput = new Scanner(System.in);
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<EmployeeLogin> employeeLogins = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();

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
        boolean result;
        for(int i = 0; i < 3; i++) {
            System.out.print("LOGIN: ");
            String login = userInput.next();

            System.out.print("PASSWORD: ");
            String password = userInput.next();
            result = doesLoginExist(login, password);
            if(result) return true;
        }
        return false;
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
                productsMenu();
                break;
            case 4:
                myAccountMenu();
                break;
            case 5:
                logout();
                break;
            case 6:
                exit();
        }
    }

    private static void logout() {
        logIn();
    }

    private static void exit() {
        System.out.println("\nThank you for using Business Management! :-)");
        System.exit(0);
    }

    private static void myAccountMenu() {
        showMyAccountMenuInterface();
    }

    private static void productsMenu() {
        showProductsMenuInterface();
    }


    private static void customersMenu() {
        showCustomersMenuInterface();
    }

    private static void ordersMenu() {
        showOrdersMenuInterface();
        switch(userInput.nextInt()) {
            case 1:
                createOrder();
                break;
        }
    }

    private static void createOrder() {
        System.out.println("*".repeat(20));
        showCustomers();
        Customer customer = chooseCustomer();
        showProducts();
        Order order = new Order(customer, chooseProducts());
        orders.add(order);
    }

    private static void showProducts() {
        for(Product product : products) {
            System.out.println(product + "\n");
        }

    }

    private static List<Product> chooseProducts() {
        List<Product> tempProducts = new ArrayList<>();
        String result;

        System.out.println("If you want to finish taking order press 'f'");
        System.out.println("If you want to quit taking order press 'q'");
        do {
            result = userInput.nextLine();
            if(result.equals("f")) break;
            else if(result.equals("q")) return new ArrayList<>();
            Product product = products.get(Integer.parseInt(result)-1);
            tempProducts.add(product);
        } while (true);

        return tempProducts;
    }

    private static void showCustomers() {
        for(Customer customer : customers) {
            System.out.println(customer + "\n");
        }
    }

    private static Customer chooseCustomer() {
        System.out.print("Choose an customer: ");
        long id = userInput.nextLong();
        return customers.stream()
                .filter(c -> c.getID() == id)
                .findFirst().orElseThrow();
    }

    private static void showProductsMenuInterface() {
        System.out.println("=".repeat(20));
        System.out.println("1. Create product");
        System.out.println("2. Delete product");
        System.out.println("3. Show products");
        System.out.println("4. Go back to home menu");
    }

    private static void showCustomersMenuInterface() {
        System.out.println("=".repeat(20));
        System.out.println("1. Show all customers");
        System.out.println("2. Find customer");
        System.out.println("3. Add customer");
        System.out.println("4. Delete customer");
        System.out.println("5. Go back to home menu");
        System.out.println("=".repeat(20) + "\n\n");
    }


    private static void showOrdersMenuInterface() {
        System.out.println("=".repeat(20));
        System.out.println("1. Create an order");
        System.out.println("2. Delete an order");
        System.out.println("3. Show orders");
        System.out.println("4. Go back to home menu");
        System.out.println("=".repeat(20) + "\n\n");
    }

    private static void showHomeMenuInterface() {
        System.out.println("=".repeat(20));
        System.out.println("1. Orders");
        System.out.println("2. Customers");
        System.out.println("3. Products");
        System.out.println("4. My account");
        System.out.println("5. Logout");
        System.out.println("=".repeat(20) + "\n\n");
    }

    private static void showMyAccountMenuInterface() {
        System.out.println("=".repeat(20));
        System.out.println("1. Show information about my account");
        System.out.println("2. Change information about my account");
        System.out.println("3. Go back to home menu");
        System.out.println("=".repeat(20) + "\n\n");
    }

    private static boolean doesLoginExist(String login, String password) {
        EmployeeLogin employeeLogin = new EmployeeLogin(login, password);

        boolean result = employeeLogins.stream()
                .anyMatch(e -> e.equals(employeeLogin));
        if(result) {
            System.out.println("\nHello! You successfully logged in!\n");
        } else {
            System.out.println("\nLogin or password are incorrect. Try again!\n");
        }
        return result;
    }


}
