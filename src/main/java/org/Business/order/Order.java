package org.Business.order;

import org.Business.customer.Customer;
import org.Business.product.Product;

import java.util.Map;

public class Order {
    private final long ID;
    private static long incrementID = 1;

    private final Customer customer;
    private final Map<Product, Integer> products;
    private final double totalPrice;
    private String orderComments;

    public Order(Customer customer, Map<Product, Integer> products) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = countTotalPrice(products);
        ID = incrementID++;
    }

    public String getOrderComments() {
        return orderComments;
    }

    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    public void addProduct(Product product) {
        products.put(product, products.get(product) + 1);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public long getID() {
        return ID;
    }


    public static double countTotalPrice(Map<Product, Integer> order) {
        double result = 0.0;
        for (Map.Entry<Product, Integer> product: order.entrySet()) {
            result += product.getValue() * product.getKey().getPrice();
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "Order: " + ID + ".\n" + customer + "\n";
        StringBuilder tempStrBuilder = new StringBuilder();
        for(Map.Entry<Product, Integer> product : products.entrySet()) {
            tempStrBuilder.append("Quantity: ").append(product.getValue()).append("\n").append(product.getKey()).append("\n");
        }
        return result += tempStrBuilder.toString();
    }


}
