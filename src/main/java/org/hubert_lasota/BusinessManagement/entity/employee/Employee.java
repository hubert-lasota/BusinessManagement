package org.hubert_lasota.BusinessManagement.entity.employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private final Long id;
    private static long incrementId = 1;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Long addressId;
    private BigDecimal salary;
    private EmployeeProfession profession;

    public Employee(String firstName,
                     String lastName,
                     LocalDate dateOfBirth,
                     Long addressId,
                     BigDecimal salary,
                     EmployeeProfession profession) {
        this.id = incrementId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.addressId = addressId;
        this.salary = salary;
        this.profession = profession;
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this(firstName, lastName, dateOfBirth, 0L, BigDecimal.ZERO, EmployeeProfession.NONE);
    }

    public Long getId() {
        return id;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public EmployeeProfession getProfession() {
        return profession;
    }

    public void setProfession(EmployeeProfession profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", salary=" + salary +
                ", profession=" + profession +
                '}';
    }

}
