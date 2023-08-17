package org.Business.customer;

public class Customer {
    private final long ID;
    private static long incrementID = 1;

    public String name;

    public Customer(String name) {
        this.name = name;
        ID = incrementID++;
    }

    public long getID() {
        return ID;
    }
}
