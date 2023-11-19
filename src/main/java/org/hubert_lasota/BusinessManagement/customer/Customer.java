package org.hubert_lasota.BusinessManagement.customer;

public class Customer {
    private final Long ID;
    private static long incrementID = 1;

    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
        ID = incrementID++;
    }

    public static class Address {
        private String streetWithNumber;
        private String postalCode;
        private String city;
        private String country;

        public Address(String streetWithNumber, String postalCode, String city, String country) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return ID + ". " + name + "\n"
                + address.streetWithNumber + "\n"
                + address.postalCode + " " + address.city + "\n"
                + address.country;
    }
}
