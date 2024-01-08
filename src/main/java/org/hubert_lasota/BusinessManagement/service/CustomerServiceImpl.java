package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.address.CustomerAddress;
import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.exception.NoCustomersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer saveCustomer(Customer customer) {
        if(isCustomerPresentInDatabase(customer.getId())) {
            return customer;
        }
        CustomerAddress.addRelation(customer.getAddressId(), customer.getId());
        return customerRepository.save(customer);
    }

    private boolean isCustomerPresentInDatabase(Long id) {
        return customerRepository.findById(id).isPresent();
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new NoCustomersInDatabaseException("There are no customers in database with this name!"));
    }

    @Override
    public List<Customer> findCustomersByAddress(Long addressId) {
        return customerRepository.findByAddressId(addressId)
                .orElseThrow(() -> new NoCustomersInDatabaseException("There are no customers in database with this address!"));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll().orElseThrow(NoCustomersInDatabaseException::new);
    }

    @Override
    public void updateCustomersName(Long id, String name) {
        Customer customerToUpdate = findCustomerById(id);
        customerToUpdate.setName(name);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer customer = findCustomerById(id);
        CustomerAddress.deleteRelation(customer.getAddressId(), id);
        customerRepository.delete(id);
    }

}
