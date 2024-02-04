package org.hubert_lasota.BusinessManagement.console.menu;

import org.hubert_lasota.BusinessManagement.entity.address.Address;
import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.exception.NoCustomersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.service.AddressService;
import org.hubert_lasota.BusinessManagement.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.*;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTable;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTableHeader;
import static org.hubert_lasota.BusinessManagement.console.ui.data.CustomerMenuUIData.*;


public class CustomerMenuManager implements Menu {
    private final CustomerService customerService;
    private final AddressService addressService;

    public CustomerMenuManager(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
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
                    showCustomersAndOpenEditor();
                    break;
                case 4:
                    findCustomersAndOpenEditor();
                    break;
                case 5:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void createCustomer() {
        do {
            Customer customer = customerForm();
            customerService.saveCustomer(customer);
            Address address = addressForm();
            addressService.saveCustomerAddress(customer.getId(), address);
            customer.setAddressId(address.getId());
        } while (continueInput("Do you want to add another customer yes/no?", "no"));
    }

    private Customer customerForm() {
        System.out.println(createTableHeader("ADDING NEW CUSTOMER"));
        System.out.print("Type customer's name: ");
        String name = readLine();
        return new Customer(name, null);
    }

    private Address addressForm() {
        System.out.print("Type customer's street with number: ");
        String addressLine = readLine();
        System.out.print("Type customer's postal code: ");
        String postalCode = readLine();
        System.out.print("Type customer's city: ");
        String city = readLine();
        System.out.print("Type customer's country: ");
        String country = readLine();
        return new Address(addressLine, postalCode, city, country);
    }

    public void deleteCustomer() {
        System.out.println(createStarBorder(CUSTOMER_MENU_DELETE_CONTENT));
        try {
            showCustomers();
        } catch (NoCustomersInDatabaseException exc) {
            System.out.println(createStarBorder(exc.getMessage()));
            return;
        }
        Long id = readLong();
        customerService.deleteCustomerById(id);
    }

    private void showCustomersAndOpenEditor() {
        try {
            showCustomers();
            openEditorOnCustomer();
        } catch (NoCustomersInDatabaseException exc) {
            System.out.println(createStarBorder(exc.getMessage()));
        }
    }

    public void showCustomers() throws NoCustomersInDatabaseException {
        System.out.println(createTableHeader("CUSTOMERS"));
        List<Customer> customers = customerService.findAllCustomers();
        customers.forEach(c -> {
            Address address = addressService.findAddressById(c.getId());
            System.out.println(c);
            System.out.println(address);
        });
    }

    private void openEditorOnCustomer() {
        String operationMessage = String.join(" ", CUSTOMER_MENU_OPEN_EDITOR_MESSAGE);
        if(continueOperation(operationMessage, "q")) {
            System.out.println("Type customer's");
            Long id = readLong();
            try {
                editCustomer(id);
            } catch (NoSuchIdException exc) {
                System.out.println(createStarBorder(exc.getMessage()));
            }
        }
    }

    private void editCustomer(Long id) throws NoSuchIdException {
        String customersName = customerService.findCustomerById(id).getName();
        System.out.println(createTable("Edit: " + customersName,
                CUSTOMER_MENU_EDITOR_CONTENT));

        int inputResult = readInt();
        switch (inputResult) {
            case 1:
                changeCustomersName(id);
                break;
            case 2:
                changeCustomersAddress(id);
                break;
            case 3:
                customerService.deleteCustomerById(id);
                break;
            case 4:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private void changeCustomersName(Long id) {
        System.out.println("Current name: " + customerService.findCustomerById(id).getName());
        System.out.print("Type new customer's name: ");
        String name = readLine();
        customerService.updateCustomersName(id, name);
    }

   private void changeCustomersAddress(Long id) {
        Customer customerToUpdate = customerService.findCustomerById(id);
        Long customerAddressId = customerToUpdate.getAddressId();
        Address currentAddress = addressService.findAddressById(customerAddressId);
        System.out.println("Current address: " + currentAddress);
        Address newAddress = addressForm();
        addressService.saveCustomerAddress(id, newAddress);
        customerToUpdate.setAddressId(newAddress.getId());
   }

    private void findCustomersAndOpenEditor() {
        try {
            findCustomers();
            openEditorOnCustomer();
        } catch (NoSuchIdException | NoCustomersInDatabaseException e) {
            System.out.println(createStarBorder(e.getMessage()));
        }
    }

    public void findCustomers()
            throws NoCustomersInDatabaseException, NoSuchIdException {
        System.out.println(createTable(CUSTOMER_MENU_FIND_CUSTOMERS_TITLE,
                CUSTOMER_MENU_FIND_CUSTOMERS_CONTENT));
        int chosenOperation = readInt();
        List<Customer> customers = findCustomersByData(chosenOperation);
        customers.forEach(System.out::println);
    }

    private List<Customer> findCustomersByData(int chosenOperation)
            throws NoCustomersInDatabaseException, NoSuchIdException {
        switch (chosenOperation) {
            case 1:
                return List.of(findCustomerById());
            case 2:
                return findCustomersByName();
            case 3:
                return findCustomersByAddressLine();
            case 4:
                return findCustomersByPostalCode();
            case 5:
                return findCustomersByCity();
            case 6:
                return findCustomersByCountry();
            default:
                WrongInputException.throwAndCatchException();
        }
        throw new NoCustomersInDatabaseException();
    }

    private Customer findCustomerById() throws NoSuchIdException {
        System.out.print("Type customer's ID: ");
        Long id = readLong();
        return customerService.findCustomerById(id);
    }

    private List<Customer> findCustomersByName() throws NoCustomersInDatabaseException {
        System.out.print("Type customer's name: ");
        String name = readLine();
        return customerService.findCustomersByName(name);
    }

    private List<Customer> findCustomersByAddressLine() throws NoCustomersInDatabaseException {
        System.out.print("Type customer's street with number: ");
        String addressLine = readLine();
        List<Address> customersAddresses = addressService.findCustomerAddressByAddressLine(addressLine);
        List<Customer> foundCustomers = new ArrayList<>();
        customersAddresses.forEach(a -> {
            List<Customer> foundCustomersBySingleAddressId = customerService.findCustomersByAddress(a.getId());
            foundCustomers.addAll(foundCustomersBySingleAddressId);
        });
        return foundCustomers;
    }

    private List<Customer> findCustomersByPostalCode() throws NoCustomersInDatabaseException {
        System.out.print("Type customer's postal code: ");
        String postalCode = readLine();
        List<Address> customersAddresses = addressService.findCustomerAddressByPostalCode(postalCode);
        List<Customer> foundCustomers = new ArrayList<>();
        customersAddresses.forEach(a -> {
            List<Customer> foundCustomersBySingleAddressId = customerService.findCustomersByAddress(a.getId());
            foundCustomers.addAll(foundCustomersBySingleAddressId);
        });
        return foundCustomers;
    }

    private List<Customer> findCustomersByCity() throws NoCustomersInDatabaseException {
        System.out.print("Type customer's city: ");
        String city = readLine();
        List<Address> customersAddresses = addressService.findCustomerAddressByCity(city);
        List<Customer> foundCustomers = new ArrayList<>();
        customersAddresses.forEach(a -> {
            List<Customer> foundCustomersBySingleAddressId = customerService.findCustomersByAddress(a.getId());
            foundCustomers.addAll(foundCustomersBySingleAddressId);
        });
        return foundCustomers;
    }

    private List<Customer> findCustomersByCountry() throws NoCustomersInDatabaseException {
        System.out.print("Type customer's country: ");
        String country = readLine();
        List<Address> customersAddresses = addressService.findCustomerAddressByCountry(country);
        List<Customer> foundCustomers = new ArrayList<>();
        customersAddresses.forEach(a -> {
            List<Customer> foundCustomersBySingleAddressId = customerService.findCustomersByAddress(a.getId());
            foundCustomers.addAll(foundCustomersBySingleAddressId);
        });
        return foundCustomers;
    }

}
