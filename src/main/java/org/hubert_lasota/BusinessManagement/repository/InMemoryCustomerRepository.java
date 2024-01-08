package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryCustomerRepository implements CustomerRepository {
    private static InMemoryCustomerRepository inMemoryCustomerRepository;
    private final Map<Long, Customer> customers;

    private InMemoryCustomerRepository() {
        customers = new HashMap<>();
    }

    public static InMemoryCustomerRepository getInstance() {
        if(inMemoryCustomerRepository == null) {
            inMemoryCustomerRepository = new InMemoryCustomerRepository();
        }
        return inMemoryCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        customers.putIfAbsent(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public Optional<List<Customer>> findByName(String name) {
        return findByData(name, Customer::getName);
    }

    private Optional<List<Customer>> findByData(String data, Function<Customer, String> fieldExtractor) {
        List<Customer> foundCustomers = customers.values().stream()
                .filter(c -> fieldExtractor.apply(c).contains(data))
                .collect(Collectors.toList());

        return foundCustomers.isEmpty() ? Optional.empty() : Optional.of(foundCustomers);
    }

    @Override
    public Optional<List<Customer>> findByAddressId(Long addressId) {
        List<Customer> foundCustomers = customers.values().stream()
                .filter(c -> c.getAddressId().equals(addressId))
                .collect(Collectors.toList());

        return foundCustomers.isEmpty() ? Optional.empty() : Optional.of(foundCustomers) ;
    }

    @Override
    public Optional<List<Customer>> findAll() {
        List<Customer> foundCustomers = List.copyOf(customers.values());
        return foundCustomers.isEmpty() ? Optional.empty() : Optional.of(foundCustomers) ;
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id).orElseThrow(NoSuchIdException::new);
        customers.remove(id, customer);
    }

    @Override
    public Long count() {
        return (long) customers.size();
    }

}
