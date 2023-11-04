package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomerRepository implements Repository<Customer> {
    private static CustomerRepository customerRepository;
    private Map<Long, Customer> customers;

    private CustomerRepository() {
        customers = new HashMap<>();
    }

    public static CustomerRepository getInstance() {
        if(customerRepository == null) {
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customers.putIfAbsent(customer.getID(), customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }

    public Optional<Customer> findByName(String name) {
        return customers.values()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    public Optional<List<Customer>> findByData(String data, Function<Customer, String> fieldExtractor) {
        List<Customer> tempList = customers.values().stream()
                .filter(c -> fieldExtractor.apply(c).contains(data))
                .collect(Collectors.toList());

        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<Customer>> findAll() {
        List<Customer> tempList = List.copyOf(customers.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList) ;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer tempCustomer = findById(id).orElseThrow(NoSuchIdException::new);
        return update(tempCustomer, customer);
    }

    private Customer update(Customer customerToUpdate, Customer customerUpdater) {
        customerToUpdate.setName(customerUpdater.getName());
        customerToUpdate.setAddress(customerUpdater.getAddress());
        return customerToUpdate;
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id).orElseThrow(NoSuchIdException::new);
        customers.remove(customer.getID(), customer);
    }

    @Override
    public Long count() {
        return (long) customers.size();
    }
}