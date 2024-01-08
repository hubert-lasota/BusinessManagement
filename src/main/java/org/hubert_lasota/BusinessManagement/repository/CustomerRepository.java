package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {

    Optional<List<Customer>> findByName(String name);

    Optional<List<Customer>> findByAddressId(Long addressId);

}
