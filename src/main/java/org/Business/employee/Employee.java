package org.Business.employee;

import java.time.LocalDate;

public class Employee {
    private final long ID;
    private static long incrementID = 1;

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private EmployeeProfession profession;
    private double salary;
    private Address address;

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private final LocalDate dateOfBirth;

        private EmployeeProfession profession = EmployeeProfession.NONE;
        private double salary = 0;
        private Address address = Address.emptyAddress();

        public Builder(String firstName, String lastName, LocalDate dateOfBirth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder profession(EmployeeProfession profession) {
            this.profession = profession;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public Builder address(String addressLine, String postalCode, String city, String country) {
            this.address.streetWithNumber = addressLine;
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

        private static Address emptyAddress() {
            return new Address("none", "none", "none", "none");
        }

        @Override
        public String toString() {
            return streetWithNumber + "\n"
                    + postalCode + " " + city + "\n"
                    + country;
        }

    }

    public long getID() {
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
