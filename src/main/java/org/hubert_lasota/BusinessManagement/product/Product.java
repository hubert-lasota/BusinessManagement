package org.hubert_lasota.BusinessManagement.product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final Long ID;
    private static long incrementID = 1;

    private String name;
    private BigDecimal price;
    private String description;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.description = "no description";
        ID = incrementID++;
    }

    public Product(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        ID = incrementID++;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return ID + ". " + name + "\n"
                + "Price: " + price + "\n"
                + "Description: " + description;
    }
}
