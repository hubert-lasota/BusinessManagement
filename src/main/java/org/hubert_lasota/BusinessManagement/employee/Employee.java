package org.hubert_lasota.BusinessManagement.employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private final Long ID;
    private static long incrementID = 1;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private EmployeeProfession profession;
    private BigDecimal salary;
    private Address address;

    private Employee(Builder builder) {
        this.ID = incrementID++;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.profession = builder.profession;
        this.salary = builder.salary;
        this.address = builder.address;
    }

    public static Builder builder(String firstName, String lastName, LocalDate dateOfBirth) {
        return new Builder(firstName, lastName, dateOfBirth);
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private final LocalDate dateOfBirth;

        private EmployeeProfession profession = EmployeeProfession.NONE;
        private BigDecimal salary = BigDecimal.ZERO;
        private Address address = Address.emptyAddress();

        private Builder(String firstName, String lastName, LocalDate dateOfBirth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder profession(EmployeeProfession profession) {
            this.profession = profession;
            return this;
        }

        public Builder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public Builder address(String streetWithNumber, String postalCode, String city, String country) {
            this.address.streetWithNumber = streetWithNumber;
            this.address.postalCode = postalCode;
            this.address.city = city;
            this.address.country = country;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
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
            return "street: " + streetWithNumber + "\n"
                    + "postal code: " + postalCode + "\n"
                    + "city: " + city + "\n"
                    + "country: " + country;
        }

    }

    public Long getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public EmployeeProfession getProfession() {
        return profession;
    }

    public void setProfession(EmployeeProfession profession) {
        this.profession = profession;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    public void setStreetWithNumber(String streetWithNumber) {
        address.streetWithNumber = streetWithNumber;
    }

    public void setPostalCode(String postalCode) {
        address.postalCode = postalCode;
    }

    public void setCity(String city) {
        address.city = city;
    }

    public void setCountry(String country) {
        address.country = country;
    }


    @Override
    public String toString() {
        return  "ID[" + ID + "]\n"
                + firstName + " " + lastName + "\n"
                + "Date of birth: " + dateOfBirth.toString() + "\n"
                + "Profession: " + profession.toString().toLowerCase() + "\n"
                + "Salary: " + salary + " PLN\n"
                + address.toString();
    }

}
