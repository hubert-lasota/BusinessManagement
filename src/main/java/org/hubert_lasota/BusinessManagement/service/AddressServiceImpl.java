package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.address.Address;
import org.hubert_lasota.BusinessManagement.entity.address.CustomerAddress;
import org.hubert_lasota.BusinessManagement.entity.address.EmployeeAddress;
import org.hubert_lasota.BusinessManagement.exception.NoAddressesInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.AddressRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public Address saveEmployeeAddress(Long employeeId, Address address) {
        if (!EmployeeAddress.isAddressAssociatedToEmployee(address.getId(), employeeId)) {
            EmployeeAddress.addRelation(address.getId(), employeeId);
        }
        return saveAddress(address);
    }

    @Override
    public Address saveCustomerAddress(Long customerId, Address address) {
        if(!CustomerAddress.isAddressAssociatedToCustomer(address.getId(), customerId)) {
            CustomerAddress.addRelation(address.getId(), customerId);
        }
        return saveAddress(address);
    }

    private Address saveAddress(Address address) {
        if(isAddressPresentInDatabase(address.getId())) {
            return address;
        }
        return addressRepository.save(address);
    }

    private boolean isAddressPresentInDatabase(Long id) {
        return addressRepository.findById(id).isPresent();
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Address> findAddressByAddressLine(String addressLine) {
        return addressRepository.findByAddressLine(addressLine)
                .orElseThrow(() -> new NoAddressesInDatabaseException("There are no addresses with this address line in database!"));
    }

    @Override
    public List<Address> findAddressByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode)
                .orElseThrow(() -> new NoAddressesInDatabaseException("There are no addresses with this postal code in database!"));
    }

    @Override
    public List<Address> findAddressByCity(String city) {
        return addressRepository.findByCity(city)
                .orElseThrow(() -> new NoAddressesInDatabaseException("There are no addresses with this city in database!"));
    }

    @Override
    public List<Address> findAddressByCountry(String country) {
        return addressRepository.findByCountry(country)
                .orElseThrow(() -> new NoAddressesInDatabaseException("There are no addresses with country in database!"));
    }

    @Override
    public List<Address> findAllEmployeesAddresses() {
        List<Address> foundAddresses = findAllAddresses().stream()
                .filter(a -> EmployeeAddress.isAddressAssociatedToAnyEmployee(a.getId()))
                .collect(Collectors.toList());
        if(foundAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException();
        }
        return foundAddresses;
    }

    @Override
    public List<Address> findAllCustomersAddresses() {
        List<Address> foundAddresses = findAllAddresses().stream()
                .filter(a -> CustomerAddress.isAddressAssociatedToAnyCustomer(a.getId()))
                .collect(Collectors.toList());
        if(foundAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException();
        }
        return foundAddresses;
    }

    @Override
    public List<Address> findAllAddresses() {
        return addressRepository.findAll()
                .orElseThrow(NoAddressesInDatabaseException::new);
    }

    @Override
    public List<Address> findEmployeeAddressByAddressLine(String addressLine) {
        List<Address> foundEmployeeAddresses = findAddressByAddressLine(addressLine)
                .stream()
                .filter(a -> EmployeeAddress.isAddressAssociatedToAnyEmployee(a.getId()))
                .collect(Collectors.toList());

        if(foundEmployeeAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no employee addresses with this address line!");
        }
        return foundEmployeeAddresses;
    }

    @Override
    public List<Address> findEmployeeAddressByPostalCode(String postalCode) {
        List<Address> foundEmployeeAddresses = findAddressByPostalCode(postalCode)
                .stream()
                .filter(a -> EmployeeAddress.isAddressAssociatedToAnyEmployee(a.getId()))
                .collect(Collectors.toList());

        if(foundEmployeeAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no employee addresses with this postal code!");
        }
        return foundEmployeeAddresses;
    }

    @Override
    public List<Address> findEmployeeAddressByCity(String city) {
        List<Address> foundEmployeeAddresses = findAddressByCity(city)
                .stream()
                .filter(a -> EmployeeAddress.isAddressAssociatedToAnyEmployee(a.getId()))
                .collect(Collectors.toList());

        if(foundEmployeeAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no employee addresses with this city!");
        }
        return foundEmployeeAddresses;
    }

    @Override
    public List<Address> findEmployeeAddressByCountry(String country) {
        List<Address> foundEmployeeAddresses = findAddressByCountry(country)
                .stream()
                .filter(a -> EmployeeAddress.isAddressAssociatedToAnyEmployee(a.getId()))
                .collect(Collectors.toList());

        if(foundEmployeeAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no employee addresses with this country!");
        }
        return foundEmployeeAddresses;
    }

    @Override
    public List<Address> findCustomerAddressByAddressLine(String addressLine) {
        List<Address> foundCustomerAddresses = findAddressByAddressLine(addressLine)
                .stream()
                .filter(a -> CustomerAddress.isAddressAssociatedToAnyCustomer(a.getId()))
                .collect(Collectors.toList());

        if(foundCustomerAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no customer addresses with this address line!");
        }
        return foundCustomerAddresses;
    }

    @Override
    public List<Address> findCustomerAddressByPostalCode(String postalCode) {
        List<Address> foundCustomerAddresses = findAddressByPostalCode(postalCode)
                .stream()
                .filter(a -> CustomerAddress.isAddressAssociatedToAnyCustomer(a.getId()))
                .collect(Collectors.toList());

        if(foundCustomerAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no customer addresses with this postal code!");
        }
        return foundCustomerAddresses;
    }

    @Override
    public List<Address> findCustomerAddressByCity(String city) {
        List<Address> foundCustomerAddresses = findAddressByCity(city)
                .stream()
                .filter(a -> CustomerAddress.isAddressAssociatedToAnyCustomer(a.getId()))
                .collect(Collectors.toList());

        if(foundCustomerAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no customer addresses with this city!");
        }
        return foundCustomerAddresses;
    }

    @Override
    public List<Address> findCustomerAddressByCountry(String country) {
        List<Address> foundCustomerAddresses = findAddressByCountry(country)
                .stream()
                .filter(a -> CustomerAddress.isAddressAssociatedToAnyCustomer(a.getId()))
                .collect(Collectors.toList());

        if(foundCustomerAddresses.isEmpty()) {
            throw new NoAddressesInDatabaseException("There are no customer addresses with this !");
        }
        return foundCustomerAddresses;
    }

    @Override
    public void deleteEmployeeAddress(Long employeeId, Long addressId) {
        EmployeeAddress.deleteRelation(addressId, employeeId);
        addressRepository.delete(addressId);
    }

    @Override
    public void deleteCustomerAddress(Long customerId, Long addressId) {
        CustomerAddress.deleteRelation(addressId, customerId);
        addressRepository.delete(addressId);
    }

}
