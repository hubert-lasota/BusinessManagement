package org.Business.customer;

import org.Business.BusinessManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CustomerManagement {
    private static final List<Customer> customers = new ArrayList<>();


    private CustomerManagement(){}


    public static void addCustomers(Customer... tempCustomers) {
        for(Customer c : tempCustomers) {
            if(!customers.contains(c)) customers.add(c);
        }
    }

    public static void showCustomers() {
        System.out.println("*".repeat(30));
        for(Customer customer : customers) {
            System.out.println(customer + "\n");
        }
        System.out.println("*".repeat(30));
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    // TODO make operations on individual customers like change name/address
    public static void customersMenu() {
        showCustomersMenuInterface();
        try(Scanner userInput = new Scanner(System.in)) {
            switch (userInput.nextInt()) {
                case 1:
                    showAllCustomers();
                    break;
                case 2:
                    findCustomers();
                    break;
                case 3:
                    // TODO addCustomer
                    break;
                case 4:
                    // TODO deleteCustomer
                    break;
                case 5:
                    BusinessManagement.homeMenu();

            }
        }
    }

    // TODO program ends after this method. Fix that bug
    private static void findCustomers() {
        System.out.println("Do you want to search by:");
        System.out.println("1. Name\t2. ID\t3. Street\t4. Postal Code\t5. City\t6. Country");
        try(Scanner userInput = new Scanner(System.in)) {
            switch (userInput.nextInt()) {
                case 1:
                    System.out.print("Type customer's name: ");
                    String name = new Scanner(System.in).nextLine();
                    findCustomersByData(Customer::getName, str -> str.contains(name));
                    break;
                case 2:
                    findCustomersByData(Customer::getID, id -> id.equals(userInput.nextLong()));
                    break;
                case 3:
                    findCustomersByData(c -> c.address.getStreetWithNumber(), str -> str.contains(userInput.next()));
                    break;
                case 4:
                    findCustomersByData(c -> c.address.getPostalCode(), str -> str.contains(userInput.next()));
                    break;
                case 5:
                    findCustomersByData(c -> c.address.getCity(), str -> str.contains(userInput.next()));
                    break;
                case 6:
                    findCustomersByData(c -> c.address.getCountry(), str -> str.contains(userInput.next()));

            }
        }
    }

    private static <T> void findCustomersByData(Function<Customer, T> fieldExtractor, Function<T, Boolean> containsChecker) {
        System.out.println("*".repeat(30));
        customers.stream()
                .filter(c -> containsChecker.apply(fieldExtractor.apply(c)))
                .forEach(System.out::println);
        System.out.println("*".repeat(30));
        System.out.println("If you want to edit or create order press customer's ID!");
        System.out.println("Or if you want to go back to customer's menu press zero!");
        try(Scanner userInput = new Scanner(System.in)) {
            openEditorOnCustomer(userInput.nextLong());
        }
    }

    private static void showAllCustomers() {
        showCustomers();
        System.out.println("If you want to edit or create order press customer's ID!");
        System.out.println("Or if you want to go back to customer's menu press zero!");
        try(Scanner userInput = new Scanner(System.in)) {
            openEditorOnCustomer(userInput.nextLong());
        }
    }

    private static void openEditorOnCustomer(long id) {
        if(id == 0) customersMenu();

        boolean isInputProperId;
        do {
            isInputProperId = customers.stream()
                    .anyMatch(c -> c.getID() == id);
            if(isInputProperId) break;
            else System.out.println("Wrong id. Press customer's ID again!");
        } while (true);

        showOpenEditorOnCustomerInterface();
        try(Scanner userInput = new Scanner(System.in)) {
            switch (userInput.nextInt()) {
                case 1:
                    // TODO editCustomer()
                    break;
                case 2:
                    // TODO deleteCustomer()
                    break;
                case 3:
                    // TODO createOrder for this customer
                    break;
                case 4:
                    customersMenu();
                    break;
            }
        }
    }

    private static void showCustomersMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***CUSTOMERS***");
        System.out.println("1. Show all customers");
        System.out.println("2. Find customer");
        System.out.println("3. Add customer");
        System.out.println("4. Delete customer");
        System.out.println("5. Go back to home menu");
        System.out.println("=".repeat(30) + "\n\n");
    }

    private static void showOpenEditorOnCustomerInterface() {
        System.out.println("=".repeat(30));
        System.out.println("1. Edit customer's data");
        System.out.println("2. Delete customer");
        System.out.println("3. Create order for this customer");
        System.out.println("4. Go back to customer's menu");
        System.out.println("=".repeat(30));
    }

}
