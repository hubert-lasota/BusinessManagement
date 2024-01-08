package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.address.Address;

import java.util.List;

public interface AddressService {

    Address saveCustomerAddress(Long customerId, Address address);

    Address saveEmployeeAddress(Long employeeId, Address address);

    Address findAddressById(Long id);

    List<Address> findAddressByAddressLine(String addressLine);

    List<Address> findAddressByPostalCode(String postalCode);

    List<Address> findAddressByCity(String city);

    List<Address> findAddressByCountry(String country);

    List<Address> findAllAddresses();

    List<Address> findEmployeeAddressByAddressLine(String addressLine);

    List<Address> findEmployeeAddressByPostalCode(String postalCode);

    List<Address> findEmployeeAddressByCity(String city);

    List<Address> findEmployeeAddressByCountry(String country);

    List<Address> findAllEmployeesAddresses();

    List<Address> findCustomerAddressByAddressLine(String addressLine);

    List<Address> findCustomerAddressByPostalCode(String postalCode);

    List<Address> findCustomerAddressByCity(String city);

    List<Address> findCustomerAddressByCountry(String country);

    List<Address> findAllCustomersAddresses();

    void deleteEmployeeAddress(Long employeeId, Long addressId);

    void deleteCustomerAddress(Long customerId, Long addressId);
}
