package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.address.Address;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryAddressRepository implements AddressRepository {
    private static InMemoryAddressRepository inMemoryAddressRepository;
    public final Map<Long, Address> addresses;

    private InMemoryAddressRepository() {
        addresses = new HashMap<>();
    }

    public static InMemoryAddressRepository getInstance() {
        if(inMemoryAddressRepository == null) {
            inMemoryAddressRepository = new InMemoryAddressRepository();
        }
        return inMemoryAddressRepository;
    }

    @Override
    public Address save(Address address) {
        addresses.putIfAbsent(address.getId(), address);
        return address;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(addresses.get(id));
    }

    @Override
    public Optional<List<Address>> findByAddressLine(String addressLine) {
        return findByData(addressLine, Address::getAddressLine);
    }

    @Override
    public Optional<List<Address>> findByPostalCode(String postalCode) {
        return findByData(postalCode, Address::getPostalCode);
    }

    @Override
    public Optional<List<Address>> findByCity(String city) {
        return findByData(city, Address::getCity);
    }

    @Override
    public Optional<List<Address>> findByCountry(String country) {
        return findByData(country, Address::getCountry);
    }

    private Optional<List<Address>> findByData(String data, Function<Address, String> fieldExtractor) {
        List<Address> foundAddresses = addresses.values().stream()
                .filter(a -> fieldExtractor.apply(a).contains(data))
                .collect(Collectors.toList());

        return foundAddresses.isEmpty() ? Optional.empty() : Optional.of(foundAddresses);
    }

    @Override
    public Optional<List<Address>> findAll() {
        List<Address> foundAddresses = List.copyOf(addresses.values());
        return foundAddresses.isEmpty() ? Optional.empty() : Optional.of(foundAddresses);
    }

    @Override
    public void delete(Long id) {
        Address address = findById(id).orElseThrow(NoSuchIdException::new);
        addresses.remove(id, address);
    }

    @Override
    public Long count() {
        return (long) addresses.size();
    }

}
