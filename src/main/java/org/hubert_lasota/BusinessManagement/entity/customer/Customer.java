package org.hubert_lasota.BusinessManagement.entity.customer;

import java.util.Objects;

public class Customer {
    private final Long id;
    private static long incrementId = 1;

    private String name;
    private Long addressId;

    public Customer(String name, Long addressId) {
        id = incrementId++;
        this.name = name;
        this.addressId = addressId;
    }


    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

}
