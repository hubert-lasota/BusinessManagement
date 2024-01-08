package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryCustomerRepositoryTest {
    InMemoryCustomerRepository inMemoryCustomerRepository = InMemoryCustomerRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryCustomerRepository.class.getDeclaredField("customers");
        field.setAccessible(true);
        Map<Long, Customer> customers = (Map<Long, Customer>) field.get(inMemoryCustomerRepository);
        customers.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = inMemoryCustomerRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Customer customer;
        for(int i = 0; i < counter; i++) {
            customer = new Customer(
                    "Customer"+i, 0L);
            inMemoryCustomerRepository.save(customer);
        }
        long repositoryCounter = inMemoryCustomerRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveCustomer() {
        Customer actual = new Customer(
                "CustomerTest", 0L);
        long repositorySizeBeforeSave = inMemoryCustomerRepository.count();

        Customer expected = inMemoryCustomerRepository.save(actual);
        long repositorySizeAfterSave = inMemoryCustomerRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleCustomers() {
        int counter = 10;
        String name;

        for(int i = 0; i < counter; i++) {
            name = "CustomerTest" + i;
            inMemoryCustomerRepository.save(new Customer(
                    name, 0L));
        }

        long repositorySizeAfterSave = inMemoryCustomerRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteCustomer() {
        Customer customer = new Customer(
                "CustomerTest",  0L);
        inMemoryCustomerRepository.save(customer);
        long repositorySizeAfterSave = inMemoryCustomerRepository.count();

        inMemoryCustomerRepository.delete(customer.getId());
        long repositorySizeAfterDelete = inMemoryCustomerRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleCustomers() {
        int counter = 10;
        Customer[] customers = new Customer[counter];
        String name;

        for(int i = 0; i < counter; i++) {
            name = "CustomerTest" + i;
            customers[i] = inMemoryCustomerRepository.save(new Customer(
                    name, 0L));
        }
        long repositorySizeAfterSave = inMemoryCustomerRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryCustomerRepository.delete(customers[i].getId());
        }
        long repositorySizeAfterDelete = inMemoryCustomerRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer(
                "customer", 0L);
        inMemoryCustomerRepository.save(customer);

        Customer foundCustomer = inMemoryCustomerRepository.findById(customer.getId()).get();

        assertEquals(customer, foundCustomer);
    }

    @Test
    void shouldNotFindById() {
        Optional<Customer> foundCustomer = inMemoryCustomerRepository.findById(-1L);
        assertEquals(Optional.empty(), foundCustomer);
    }


    @Test
    void shouldFindByName() {
        Customer customer = new Customer(
                "customer", 0L);
        inMemoryCustomerRepository.save(customer);

        Customer foundCustomer = inMemoryCustomerRepository.findByName("customer").get().get(0);
        assertEquals(customer, foundCustomer);
    }

    @Test
    void shouldNotFindByName() {
        Optional<List<Customer>> foundCustomer = inMemoryCustomerRepository.findByName("empty name");
        assertEquals(Optional.empty(), foundCustomer);
    }


    @Test
    void shouldFindByAddressId() {
        Customer customer = new Customer(
                "customer", 0L);
        inMemoryCustomerRepository.save(customer);

        Customer foundCustomer = inMemoryCustomerRepository.findByAddressId(customer.getAddressId()).get().get(0);

        assertEquals(customer, foundCustomer);
    }

    @Test
    void shouldNotFindByAddressId() {
        Optional<List<Customer>> foundCustomer = inMemoryCustomerRepository.findByAddressId(-1L);
        assertEquals(Optional.empty(), foundCustomer);
    }

    @Test
    void shouldFindAll() {
        Customer customer1 = new Customer(
                "Test", 0L);
        Customer customer2 = new Customer(
                "Test", 0L);
        inMemoryCustomerRepository.save(customer1);
        inMemoryCustomerRepository.save(customer2);
        long repositorySizeAfterSave = inMemoryCustomerRepository.count();

        List<Customer> foundCustomers = inMemoryCustomerRepository.findAll().get();
        long foundCustomersSize = foundCustomers.size();

        assertEquals(repositorySizeAfterSave, foundCustomersSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Customer>> foundCustomer = inMemoryCustomerRepository.findAll();
        assertEquals(Optional.empty(), foundCustomer);
    }

}
