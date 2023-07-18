package org.Business.employee;

import java.time.LocalDate;


public class EmployeeData {
    private final int ID;
    private static int incrementID = 1;

    private final String name;
    private final String lastname;
    private final LocalDate dateOfBirth;
    private final String profession;
    private final double salary;
    private final Address address;

    public static class Builder {
        private final String name;
        private final String lastname;
        private final LocalDate dateOfBirth;

        private String profession = "none";
        private double salary = 0;
        private Address address = Address.emptyAddress();

        public Builder(String name, String lastname, LocalDate dateOfBirth) {
            this.name = name;
            this.lastname = lastname;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder profession(String profession) {
            this.profession = profession;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public Builder address(String addressLine, String postalCode, String city, String country) {
            this.address.addressLine = addressLine;
            this.address.postalCode = postalCode;
            this.address.city = city;
            this.address.country = country;
            return this;
        }

        public EmployeeData build() {
            return new EmployeeData(this);
        }
    }

    private EmployeeData(Builder builder) {
        this.ID = incrementID++;
        this.name = builder.name;
        this.lastname = builder.lastname;
        this.dateOfBirth = builder.dateOfBirth;
        this.profession = builder.profession;
        this.salary = builder.salary;
        this.address = builder.address;
    }

    private static class Address {
        private String addressLine; // street including its number
        private String postalCode;
        private String city;
        private String country;

        private Address(String addressLine, String postalCode, String city, String country) {
            this.addressLine = addressLine;
            this.postalCode = postalCode;
            this.city = city;
            this.country = country;
        }

        private static Address emptyAddress() {
            return new Address("none", "none", "none", "none");
        }

        @Override
        public String toString() {
            return addressLine + "\n"
                    + postalCode + " " + city + "\n"
                    + country;
        }

    }

    public int getID() {
        return ID;
    }



    @Override
    public String toString() {
        return  "ID[" + ID + "]\n"
                + name + " " + lastname + "\n"
                + "Date of birth: " + dateOfBirth.toString() + "\n"
                + profession + "\n" + "Salary: " + salary + "PLN\n"
                + address.toString();
    }

}
