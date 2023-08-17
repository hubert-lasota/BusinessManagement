package org.Business.order;

import org.Business.customer.Customer;
import org.Business.product.Product;

import java.util.List;

public class Order {
    private final long ID;
    private static long incrementID = 1;

    private final Customer customer;
    private final List<Product> products;
    private String orderComments;

    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
        ID = incrementID++;
    }

    public String getOrderComments() {
        return orderComments;
    }

    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }


}
