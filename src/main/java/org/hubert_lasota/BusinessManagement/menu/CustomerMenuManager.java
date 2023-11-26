package org.hubert_lasota.BusinessManagement.menu;

import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.hubert_lasota.BusinessManagement.exception.NoCustomersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.repository.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hubert_lasota.BusinessManagement.input.UserInputReader.*;
import static org.hubert_lasota.BusinessManagement.ui.CustomerMenuUIData.*;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;


public class CustomerMenuManager implements Menu {
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

    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(CUSTOMER_MENU_TITLE, CUSTOMER_MENU_CONTENT));
            int inputResult = readInt();
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
                        break;
                    }
                    openEditorOnCustomer();
                    break;
                case 4:
                    if(findCustomers()) {
                        openEditorOnCustomer();
                    }
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
            String continueInput = readLine();
            if (continueInput.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private Customer customerForm() {
        System.out.println(createTitleOfTable("ADDING NEW CUSTOMER"));
        System.out.print("Type customer's name: ");
        String name = readLine();
        System.out.print("Type customer's street with number: ");
        String streetWithNumber = readLine();
        System.out.print("Type customer's postal code: ");
        String postalCode = readLine();
        System.out.print("Type customer's city: ");
        String city = readLine();
        System.out.print("Type customer's country: ");
        String country = readLine();

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
        Long id = readLong();
        customerRepository.delete(id);
    }

    public void showCustomers() {
        System.out.println(createTitleOfTable("CUSTOMERS"));
        List <Customer> customers;

        customers = customerRepository.findAll()
                    .orElseThrow(NoCustomersInDatabaseException::new);

        customers.forEach(System.out::println);
    }

    private void openEditorOnCustomer() {
        System.out.println(createStarFrame(CUSTOMER_MENU_OPEN_EDITOR_MESSAGE));

        String inputResult = readLine();
        if(!(inputResult.equals("q"))) {
            Long id = Long.parseLong(inputResult);
            Customer customer;
            try {
                customer = customerRepository.findById(id).orElseThrow(NoSuchIdException::new);
            } catch (NoSuchIdException e) {
                System.out.println(createStarFrame(e.getMessage()));
                return;
            }
            editCustomer(customer);
        }
    }

    private void editCustomer(Customer customerToUpdate) {
        System.out.println(createTable("Edit: " + customerToUpdate.getName(), CUSTOMER_MENU_EDITOR_CONTENT));

        int inputResult = readInt();
        switch (inputResult) {
            case 1:
                updateCustomersName(customerToUpdate);
                break;
            case 2:
                updateCustomersStreetWithNumber(customerToUpdate);
                break;
            case 3:
                updateCustomersPostalCode(customerToUpdate);
                break;
            case 4:
                updateCustomersCity(customerToUpdate);
                break;
            case 5:
                updateCustomersCountry(customerToUpdate);
                break;
            case 6:
                customerRepository.delete(customerToUpdate.getID());
                break;
            case 7:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private void updateCustomersName(Customer customerToUpdate) {
        System.out.println("Type new customer's name: ");
        String name = readLine();
        customerToUpdate.setName(name);
    }

    private void updateCustomersStreetWithNumber(Customer customerToUpdate) {
        System.out.println("Type new customer's street with number: ");
        String streetWithNumber = readLine();
        customerToUpdate.setStreetWithNumber(streetWithNumber);
    }

    private void updateCustomersPostalCode(Customer customerToUpdate) {
        System.out.println("Type new customer's postal code: ");
        String postalCode = readLine();
        customerToUpdate.setPostalCode(postalCode);
    }

    private void updateCustomersCity(Customer customerToUpdate) {
        System.out.println("Type new customer's city: ");
        String city = readLine();
        customerToUpdate.setCity(city);
    }

    private void updateCustomersCountry(Customer customerToUpdate) {
        System.out.println("Type new customer's country: ");
        String country = readLine();
        customerToUpdate.setCountry(country);
    }

    public boolean findCustomers() {
        System.out.println(createTable(CUSTOMER_MENU_FIND_CUSTOMERS_TITLE, CUSTOMER_MENU_FIND_CUSTOMERS_CONTENT));
        int result = readInt();

        List<Customer> customers = findCustomersByData(result);
        if(!customers.isEmpty()) {
            customers.forEach(System.out::println);
            return true;
        }
        return false;
    }

    private List<Customer> findCustomersByData(int inputResult) {
        switch (inputResult) {
            case 1:
                return findCustomerByIdAndHandleException();
            case 2:
                return findCustomersByDataAndHandleException("name", Customer::getName);
            case 3:
                return findCustomersByDataAndHandleException("street with number", Customer::getStreetWithNumber);
            case 4:
                return findCustomersByDataAndHandleException("postal code", Customer::getPostalCode);
            case 5:
                return findCustomersByDataAndHandleException("city", Customer::getCity);
            case 6:
                return findCustomersByDataAndHandleException("country", Customer::getCountry);
            default:
                WrongInputException.throwAndCatchException();
        }

        return Collections.emptyList();
    }

    private List<Customer> findCustomerByIdAndHandleException() {
        try {
            return List.of(findCustomerById());
        } catch (NoSuchIdException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return Collections.emptyList();
        }
    }

   private List<Customer> findCustomersByDataAndHandleException(
           String dataFieldToPrint, Function<Customer, String> fieldExtractor) {

        try {
            return findCustomersByData(dataFieldToPrint, fieldExtractor);
        } catch (NoCustomersInDatabaseException exc) {
            System.out.println(createStarFrame(exc.getMessage()));
            return Collections.emptyList();
        }
   }


    private Customer findCustomerById() {
        System.out.print("Type customer's ID: ");
        Long id = readLong();
        return customerRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    private List<Customer> findCustomersByData(String dataFieldToPrint, Function<Customer, String> fieldExtractor) {
        System.out.println("Type customer's " + dataFieldToPrint + ": ");
        String data = readLine();
        return customerRepository.findByData(data, fieldExtractor)
                        .orElseThrow(() -> new NoCustomersInDatabaseException("There are no customers with this data!"));
    }

}
