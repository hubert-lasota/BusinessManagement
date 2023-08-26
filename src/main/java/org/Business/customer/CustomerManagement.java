package org.Business.customer;

import org.Business.BusinessManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.Business.BusinessManagement.userInput;

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

    public static void customersMenu() {
        showCustomersMenuInterface();
        int inputResult = userInput.nextInt();
        userInput.nextLine();
        switch (inputResult) {
            case 1:
                showAllCustomers();
                break;
            case 2:
                findCustomers();
                break;
            case 3:
                addCustomer();
                break;
            case 4:
                deleteCustomer();
                break;
            case 5:
                BusinessManagement.homeMenu();


        }
    }

    private static void deleteCustomer() {
        System.out.println("Type customer's ID which you want to delete: ");
        long id = userInput.nextLong();
        userInput.nextLine(); // cleans the buffer

        deleteCustomerById(id);

    }

    private static void deleteCustomerById(long id) {
        System.out.println("Are you sure to delete this customer? Type yes/no.");
        customers.stream()
                .filter(c -> c.getID() == id)
                .forEach(System.out::println);

        if (userInput.nextLine().equals("yes")) {
                if (customers.removeIf(c -> c.getID() == id)) {
                    System.out.println("Customer successfully removed from database!");
                } else {
                    System.out.println("ID is incorrect.");
                }
                customersMenu();
        } else {
            customersMenu();
        }
    }

    private static void addCustomer() {
        System.out.println("Enter customer data!");
        System.out.print("Name: ");
        String name = userInput.nextLine();
        System.out.print("Street with number: ");
        String streetWithNumber = userInput.nextLine();
        System.out.print("Postal code: ");
        String postalCode = userInput.nextLine();
        System.out.print("City: ");
        String city = userInput.nextLine();
        System.out.print("Country: ");
        String country = userInput.nextLine();

        Customer.Address address = new Customer.Address(streetWithNumber, postalCode, city, country);
        Customer customer = new Customer(name, address);

        if(!customers.contains(customer)) {
            System.out.println("\nCUSTOMER ADDED SUCCESSFULLY!\n");
            customers.add(customer);
            customersMenu();
        } else {
            System.out.println("\nCUSTOMER EXISTS IN DATABASE!\n");
            customersMenu();
        }
    }

    private static void findCustomers() {
        System.out.println("Do you want to search by:");
        System.out.println("1. Name\t2. ID\t3. Street\t4. Postal Code\t5. City\t6. Country");

        int resultInput = userInput.nextInt();;
        userInput.nextLine(); // cleans the buffer

        switch (resultInput) {
            case 1:
                System.out.print("Type customer's name: ");
                String name = userInput.nextLine();
                findCustomersByData(Customer::getName, str -> str.contains(name));
                break;
            case 2:
                System.out.print("Type customer's id: ");
                long ID = userInput.nextLong();
                userInput.nextLine();
                findCustomersByData(Customer::getID, id -> id.equals(ID));
                break;
            case 3:
                System.out.print("Type customer's street: ");
                String street = userInput.nextLine();
                findCustomersByData(c -> c.address.getStreetWithNumber(), str -> str.contains(street));
                break;
            case 4:
                System.out.print("Type customer's postal code: ");
                String postalCode = userInput.nextLine();
                findCustomersByData(c -> c.address.getPostalCode(), str -> str.contains(postalCode));
                break;
            case 5:
                System.out.print("Type customer's city: ");
                String city = userInput.nextLine();
                findCustomersByData(c -> c.address.getCity(), str -> str.contains(city));
                break;
            case 6:
                System.out.print("Type customer's country: ");
                String country = userInput.nextLine();
                findCustomersByData(c -> c.address.getCountry(), str -> str.contains(country));


        }
    }


    private static <T> void findCustomersByData(Function<Customer, T> fieldExtractor, Function<T, Boolean> containsChecker) {
        System.out.println("*".repeat(30));
        customers.stream()
                .filter(c -> containsChecker.apply(fieldExtractor.apply(c)))
                .forEach(c -> System.out.println(c + "\n"));
        System.out.println("*".repeat(30));
        System.out.println("If you want to edit or create order press customer's ID!");
        System.out.println("Or if you want to go back to customer's menu press zero!");

        long id = userInput.nextLong();
        userInput.nextLine();
        openEditorOnCustomer(id);

    }

    private static void showAllCustomers() {
        showCustomers();
        System.out.println("If you want to edit or create order press customer's ID!");
        System.out.println("Or if you want to go back to customer's menu press zero!");

        long id = userInput.nextLong();
        userInput.nextLine();
        openEditorOnCustomer(id);

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
        int inputResult = userInput.nextInt();
        userInput.nextLine();
        switch (inputResult) {
            case 1:
                editCustomer(id);
                break;
            case 2:
                deleteCustomerById(id);
                break;
            case 3:
                // TODO createOrder for this customer
                break;
            case 4:
                customersMenu();
                break;

        }
    }

    private static void editCustomer(long id) {
        System.out.println("Do you want to edit: ");
        System.out.println("1.Name\t2.Street with number\t3.Postal code\t4.City\t5.Country");
        int inputResult = userInput.nextInt();
        userInput.nextLine(); // cleans the buffer
        switch (inputResult) {
            case 1:
                System.out.println("Type new customer's name!");
                customers.get(getIndexOfCustomer(id)).setName(userInput.nextLine());
                break;
            case 2:
                System.out.println("Type new customer's street with number!");
                customers.get(getIndexOfCustomer(id)).address.setStreetWithNumber(userInput.nextLine());
                break;
            case 3:
                System.out.println("Type new customer's postal code!");
                customers.get(getIndexOfCustomer(id)).address.setPostalCode(userInput.nextLine());
                break;
            case 4:
                System.out.println("Type new customer's city!");
                customers.get(getIndexOfCustomer(id)).address.setCity(userInput.nextLine());
                break;
            case 5:
                System.out.println("Type new customer's country!");
                customers.get(getIndexOfCustomer(id)).address.setCountry(userInput.nextLine());
        }
        customersMenu();
    }

    private static int getIndexOfCustomer(long id) {
        Customer customer = customers.stream()
                .filter(c -> c.getID() == id)
                .findFirst().get();
        return customers.indexOf(customer);
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
        System.out.println("2. Delete this customer");
        System.out.println("3. Create order for this customer");
        System.out.println("4. Go back to customer's menu");
        System.out.println("=".repeat(30));
    }

}
