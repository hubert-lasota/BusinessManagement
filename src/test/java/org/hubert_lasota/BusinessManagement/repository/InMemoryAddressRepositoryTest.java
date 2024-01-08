package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.address.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryAddressRepositoryTest {

    InMemoryAddressRepository inMemoryAddressRepository = InMemoryAddressRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryAddressRepository.class.getDeclaredField("addresses");
        field.setAccessible(true);
        Map<Long, Address> addresses = (Map<Long, Address>) field.get(inMemoryAddressRepository);
        addresses.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = inMemoryAddressRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Address address;
        for (int i = 0; i < counter; i++) {
            address = new Address(
                    "Street" + i, "PostalCode" + i, "City" + i, "Country" + i);
            inMemoryAddressRepository.save(address);
        }
        long repositoryCounter = inMemoryAddressRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveAddress() {
        Address actual = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        long repositorySizeBeforeSave = inMemoryAddressRepository.count();

        Address expected = inMemoryAddressRepository.save(actual);
        long repositorySizeAfterSave = inMemoryAddressRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave - 1);
    }

    @Test
    void shouldSaveMultipleAddresses() {
        int counter = 10;
        String street;

        for (int i = 0; i < counter; i++) {
            street = "Test Street" + i;
            inMemoryAddressRepository.save(new Address(
                    street, "Test PostalCode", "Test City", "Test Country"));
        }

        long repositorySizeAfterSave = inMemoryAddressRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteAddress() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);
        long repositorySizeAfterSave = inMemoryAddressRepository.count();

        inMemoryAddressRepository.delete(address.getId());
        long repositorySizeAfterDelete = inMemoryAddressRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete + 1);
    }

    @Test
    void shouldDeleteMultipleAddresses() {
        int counter = 10;
        Address[] addresses = new Address[counter];
        String street;

        for (int i = 0; i < counter; i++) {
            street = "Test Street" + i;
            addresses[i] = inMemoryAddressRepository.save(new Address(
                    street, "Test PostalCode", "Test City", "Test Country"));
        }
        long repositorySizeAfterSave = inMemoryAddressRepository.count();

        for (int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryAddressRepository.delete(addresses[i].getId());
        }
        long repositorySizeAfterDelete = inMemoryAddressRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);

        Address foundAddress = inMemoryAddressRepository.findById(address.getId()).get();

        assertEquals(address, foundAddress);
    }

    @Test
    void shouldNotFindById() {
        Optional<Address> foundAddress = inMemoryAddressRepository.findById(-1L);
        assertEquals(Optional.empty(), foundAddress);
    }

    @Test
    void shouldFindByAddressLine() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);

        List<Address> foundAddresses = inMemoryAddressRepository.findByAddressLine("Test Street").get();
        assertEquals(1, foundAddresses.size());
        assertEquals(address, foundAddresses.get(0));
    }

    @Test
    void shouldNotFindByAddressLine() {
        Optional<List<Address>> foundAddresses = inMemoryAddressRepository.findByAddressLine("Nonexistent");
        assertEquals(Optional.empty(), foundAddresses);
    }

    @Test
    void shouldFindByPostalCode() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);

        List<Address> foundAddresses = inMemoryAddressRepository.findByPostalCode("Test PostalCode").get();
        assertEquals(1, foundAddresses.size());
        assertEquals(address, foundAddresses.get(0));
    }

    @Test
    void shouldNotFindByPostalCode() {
        Optional<List<Address>> foundAddresses = inMemoryAddressRepository.findByPostalCode("Nonexistent");
        assertEquals(Optional.empty(), foundAddresses);
    }

    @Test
    void shouldFindByCity() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);

        List<Address> foundAddresses = inMemoryAddressRepository.findByCity("Test City").get();
        assertEquals(1, foundAddresses.size());
        assertEquals(address, foundAddresses.get(0));
    }

    @Test
    void shouldNotFindByCity() {
        Optional<List<Address>> foundAddresses = inMemoryAddressRepository.findByCity("Nonexistent");
        assertEquals(Optional.empty(), foundAddresses);
    }

    @Test
    void shouldFindByCountry() {
        Address address = new Address(
                "Test Street", "Test PostalCode", "Test City", "Test Country");
        inMemoryAddressRepository.save(address);

        List<Address> foundAddresses = inMemoryAddressRepository.findByCountry("Test Country").get();
        assertEquals(1, foundAddresses.size());
        assertEquals(address, foundAddresses.get(0));
    }

    @Test
    void shouldNotFindByCountry() {
        Optional<List<Address>> foundAddresses = inMemoryAddressRepository.findByCountry("Nonexistent");
        assertEquals(Optional.empty(), foundAddresses);
    }

    @Test
    void shouldFindAll() {
        Address address1 = new Address(
                "Test Street 1", "Test PostalCode 1", "Test City 1", "Test Country 1");
        Address address2 = new Address(
                "Test Street 2", "Test PostalCode 2", "Test City 2", "Test Country 2");
        inMemoryAddressRepository.save(address1);
        inMemoryAddressRepository.save(address2);
        long repositorySizeAfterSave = inMemoryAddressRepository.count();

        List<Address> foundAddresses = inMemoryAddressRepository.findAll().get();
        long foundAddressesSize = foundAddresses.size();

        assertEquals(repositorySizeAfterSave, foundAddressesSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Address>> foundAddresses = inMemoryAddressRepository.findAll();
        assertEquals(Optional.empty(), foundAddresses);
    }

}
