package org.Business.employee;

import java.time.LocalDate;

public class Employee {
    private final int ID;
    private static int incrementID = 1;

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final EmployeeProffesion profession;
    private final double salary;
    private final Address address;

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private final LocalDate dateOfBirth;

        private EmployeeProffesion profession = EmployeeProffesion.NONE;
        private double salary = 0;
        private Address address = Address.emptyAddress();

        public Builder(String firstName, String lastName, LocalDate dateOfBirth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder profession(EmployeeProffesion profession) {
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

        public Employee build() {
            return new Employee(this);
        }
    }

    private Employee(Builder builder) {
        this.ID = incrementID++;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
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
                + firstName + " " + lastName + "\n"
                + "Date of birth: " + dateOfBirth.toString() + "\n"
                + profession.toString().toLowerCase() + "\n" + "Salary: " + salary + "PLN\n"
                + address.toString();
    }

}
