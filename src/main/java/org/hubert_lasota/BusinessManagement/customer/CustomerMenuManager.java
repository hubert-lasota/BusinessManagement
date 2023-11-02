package org.hubert_lasota.BusinessManagement.customer;

import org.hubert_lasota.BusinessManagement.exception.NoCustomersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hubert_lasota.BusinessManagement.BusinessManagementConsole.userInput;
import static org.hubert_lasota.BusinessManagement.ui.CustomerMenuUIData.*;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;

public class CustomerMenuManager {
    private static CustomerMenuManager customerMenuManager;
    private CustomerRepository customerRepository;

    private CustomerMenuManager() {
        customerRepository = CustomerRepository.getInstance();
    }

    public static CustomerMenuManager getInstance() {
        if(customerMenuManager == null) {
            customerMenuManager = new CustomerMenuManager();
        }
        return customerMenuManager;
    }


    public void customerMenu() {
        while (true) {
            System.out.println(createTable(CUSTOMER_MENU_TITLE, CUSTOMER_MENU_CONTENT));
            int inputResult = userInput.nextInt();
            userInput.nextLine();
            switch (inputResult) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    deleteCustomer();
                    break;
                case 3:
                    try {
                        showCustomers();
                    } catch (NoCustomersInDatabaseException exc) {
                        System.out.println(createStarFrame(exc.getMessage()));
                    }
                    break;
                case 4:
                    findCustomers();
                    break;
                case 5:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void createCustomer() {
        while (true) {
            Customer customer = customerForm();
            if(isCustomerPresentInDatabase(customer)) {
                System.out.println(createStarFrame("This customer already exist in database!"));
            } else {
                System.out.println(createStarFrame("You have successfully added new customer!"));
                customerRepository.save(customer);
            }

            System.out.println(createTableFrame("Do you want to add another customer y/n?"));
            String continueInput = userInput.nextLine();
            if (continueInput.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private Customer customerForm() {
        System.out.println(createTitleOfTable("ADDING NEW CUSTOMER"));
        System.out.print("Type customer's name: ");
        String name = userInput.nextLine();
        System.out.print("Type customer's street with number: ");
        String streetWithNumber = userInput.nextLine();
        System.out.print("Type customer's postal code: ");
        String postalCode = userInput.nextLine();
        System.out.print("Type customer's city: ");
        String city = userInput.nextLine();
        System.out.print("Type customer's country: ");
        String country = userInput.nextLine();

        Customer.Address address = new Customer.Address(streetWithNumber, postalCode, city, country);
        return new Customer(name, address);
    }

    private boolean isCustomerPresentInDatabase(Customer customer) {
        Optional<Customer> tempCustomer = customerRepository.findByName(customer.getName());
        return tempCustomer.isPresent();
    }

    public void deleteCustomer() {
        System.out.println(createStarFrame(CUSTOMER_MENU_DELETE_CONTENT));
        try {
            showCustomers();
        } catch (NoCustomersInDatabaseException exc) {
            System.out.println(createStarFrame(exc.getMessage()));
            return;
        }
        Long id = userInput.nextLong();
        userInput.nextLine();
        customerRepository.delete(id);
    }

    public void showCustomers() throws NoCustomersInDatabaseException {
        System.out.println(createTitleOfTable("CUSTOMERS"));
        List <Customer> customers;

        customers = customerRepository.findAll()
                    .orElseThrow(NoCustomersInDatabaseException::new);

        customers.forEach(System.out::println);
    }

    public void findCustomers() {
        System.out.println(createTable(CUSTOMER_MENU_FIND_CUSTOMERS_TITLE, CUSTOMER_MENU_FIND_CUSTOMERS_CONTENT));
        int result = userInput.nextInt();
        userInput.nextLine();
        List<Customer> customers;
        try {
            customers = findCustomersByData(result);
        } catch (NoCustomersInDatabaseException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return;
        }

        customers.forEach(System.out::println);
    }

    private List<Customer> findCustomersByData(int result) throws NoCustomersInDatabaseException {
        switch (result) {
            case 1:
                return List.of(findCustomerById());
            case 2:
                return findCustomersByData("name", Customer::getName);
            case 3:
                return findCustomersByData("street with number", Customer::getStreetWithNumber);
            case 4:
                return findCustomersByData("postal code", Customer::getPostalCode);
            case 5:
                return findCustomersByData("city", Customer::getCity);
            case 6:
                return findCustomersByData("country", Customer::getCountry);
        }

        WrongInputException.throwAndCatchException();
        return Collections.emptyList();
    }


    private Customer findCustomerById() {
        System.out.print("Type customer's ID: ");
        Long id = userInput.nextLong();
        userInput.nextLine();
        return customerRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    private List<Customer> findCustomersByData(String dataFieldToPrint, Function<Customer, String> fieldExtractor)
            throws NoCustomersInDatabaseException {
        System.out.println("Type customer's " + dataFieldToPrint + ": ");
        String data = userInput.nextLine();
        return customerRepository.findByData(data, fieldExtractor).orElseThrow(NoCustomersInDatabaseException::new);
    }



}
