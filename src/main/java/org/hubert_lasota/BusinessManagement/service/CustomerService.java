package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Customer findCustomerById(Long id);

    List<Customer> findCustomersByName(String name);

    List<Customer> findCustomersByAddress(Long addressId);

    List<Customer> findAllCustomers();

    void updateCustomersName(Long id, String name);

    void deleteCustomerById(Long id);

}
