package org.hubert_lasota.BusinessManagement.entity.product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final Long id;
    private static long incrementID = 1;

    private String name;
    private BigDecimal price;
    private String description;

    public Product(String name, BigDecimal price) {
        id = incrementID++;
        this.name = name;
        this.price = price;
        this.description = "no description";
    }

    public Product(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        id = incrementID++;
    }

    public Long getId() {
        return id;
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
        return Objects.equals(id, product.id) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return id + ". " + name + "\n"
                + "Price: " + price + "\n"
                + "Description: " + description;
    }

}
