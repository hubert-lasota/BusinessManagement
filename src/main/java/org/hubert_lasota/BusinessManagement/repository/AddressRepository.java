package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.address.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends Repository<Address, Long> {

    Optional<List<Address>> findByAddressLine(String addressLine);

    Optional<List<Address>> findByPostalCode(String postalCode);

    Optional<List<Address>> findByCity(String city);

    Optional<List<Address>> findByCountry(String country);

}
