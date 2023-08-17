package org.Business.product;

public class Product {
    private final long ID;
    private static long incrementID = 1;

    private String name;
    private long price;
    private String description;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
        ID = incrementID++;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
