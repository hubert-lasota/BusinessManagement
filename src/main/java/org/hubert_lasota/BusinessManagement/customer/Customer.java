package org.hubert_lasota.BusinessManagement.customer;

import java.util.Objects;

public class Customer {
    private final Long ID;
    private static long incrementID = 1;

    private String name;
    private Address address;

    public Customer(String name, String streetWithNumber, String postalCode, String city, String country) {
        this.name = name;
        this.address = new Address(streetWithNumber, postalCode, city, country);
        ID = incrementID++;
    }

    private static class Address {
        private String streetWithNumber;
        private String postalCode;
        private String city;
        private String country;

        private Address(String streetWithNumber, String postalCode, String city, String country) {
            this.streetWithNumber = streetWithNumber;
            this.postalCode = postalCode;
            this.city = city;
            this.country = country;
        }

    }

    public Long getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStreetWithNumber() {
        return address.streetWithNumber;
    }

    public void setStreetWithNumber(String streetWithNumber) {
        this.address.streetWithNumber = streetWithNumber;
    }

    public String getPostalCode() {
        return address.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.address.postalCode = postalCode;
    }

    public String getCity() {
        return address.city;
    }

    public void setCity(String city) {
        this.address.city = city;
    }

    public String getCountry() {
        return address.country;
    }

    public void setCountry(String country) {
        this.address.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(ID, customer.ID) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }

    @Override
    public String toString() {
        return ID + ". " + name + "\n"
                + address.streetWithNumber + "\n"
                + address.postalCode + " " + address.city + "\n"
                + address.country;
    }
}
