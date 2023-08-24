package org.Business.customer;

public class Customer {
    private final long ID;
    private static long incrementID = 1;

    public String name;
    public Address address;

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

        public String getStreetWithNumber() {
            return streetWithNumber;
        }

        public void setStreetWithNumber(String streetWithNumber) {
            this.streetWithNumber = streetWithNumber;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
