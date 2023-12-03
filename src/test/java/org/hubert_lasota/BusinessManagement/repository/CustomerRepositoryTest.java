package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRepositoryTest {
    CustomerRepository customerRepository = CustomerRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = CustomerRepository.class.getDeclaredField("customers");
        field.setAccessible(true);
        Map<Long, Customer> customers = (Map<Long, Customer>) field.get(customerRepository);
        customers.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = customerRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Customer customer;
        for(int i = 0; i < counter; i++) {
            customer = new Customer(
                    "Customer"+i, "swn1", "33-233", "city", "country");
            customerRepository.save(customer);
        }
        long repositoryCounter = customerRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveCustomer() {
        Customer actual = new Customer(
                "CustomerTest", "swn", "00-000","city", "country");
        long repositorySizeBeforeSave = customerRepository.count();

        Customer expected = customerRepository.save(actual);
        long repositorySizeAfterSave = customerRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleCustomers() {
        int counter = 10;
        String name;

        for(int i = 0; i < counter; i++) {
            name = "CustomerTest" + i;
            customerRepository.save(new Customer(
                    name, "swn","00-000", "City", "Country"));
        }

        long repositorySizeAfterSave = customerRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteCustomer() {
        Customer customer = new Customer(
                "CustomerTest", "swn", "00-000","city", "Country");
        customerRepository.save(customer);
        long repositorySizeAfterSave = customerRepository.count();

        customerRepository.delete(customer.getID());
        long repositorySizeAfterDelete = customerRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleCustomers() {
        int counter = 10;
        Customer[] customers = new Customer[counter];
        String name;

        for(int i = 0; i < counter; i++) {
            name = "CustomerTest" + i;
            customers[i] = customerRepository.save(new Customer(
                    name, "swn","00-000", "City", "Country"));
        }
        long repositorySizeAfterSave = customerRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            customerRepository.delete(customers[i].getID());
        }
        long repositorySizeAfterDelete = customerRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer(
                "customer", "swn", "00-000","city","country");
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(customer.getID()).get();

        assertEquals(customer, foundCustomer);
    }

    @Test
    void shouldNotFindById() {
        Optional<Customer> foundCustomer = customerRepository.findById(-1L);
        assertEquals(Optional.empty(), foundCustomer);
    }

    @Test
    void shouldFindByData() {
        Customer customer1 = new Customer(
                "Test", "swn", "00-000", "City", "Country");
        Customer customer2 = new Customer(
                "Test", "swn", "00-000", "City", "Country");
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> foundCustomers = customerRepository.findByData("Test", Customer::getName).get();

        assertEquals(customer1, foundCustomers.get(0));
        assertEquals(customer2, foundCustomers.get(1));
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<Customer>> foundCustomer = customerRepository.findByData("This data doesn't exists", Customer::getName);
        assertEquals(Optional.empty(), foundCustomer);
    }

    @Test
    void shouldFindAll() {
        Customer customer1 = new Customer(
                "Test", "swn", "00-000", "City", "Country");
        Customer customer2 = new Customer(
                "Test", "swn", "00-000", "City", "Country");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        long repositorySizeAfterSave = customerRepository.count();

        List<Customer> foundCustomers = customerRepository.findAll().get();
        long foundCustomersSize = foundCustomers.size();

        assertEquals(repositorySizeAfterSave, foundCustomersSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Customer>> foundCustomer = customerRepository.findAll();
        assertEquals(Optional.empty(), foundCustomer);
    }
}
