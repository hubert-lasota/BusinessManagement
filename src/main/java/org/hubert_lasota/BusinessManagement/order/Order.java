package org.hubert_lasota.BusinessManagement.order;

import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.hubert_lasota.BusinessManagement.product.Product;

import java.math.BigDecimal;
import java.util.Map;

public class Order {
    private final Long ID;
    private static long incrementID = 1;
    private final Customer customer;
    private Map<Product, Integer> products;
    private BigDecimal totalPrice;
    private String orderComments;
    private OrderStatus orderStatus;

    public Order(Customer customer, Map<Product, Integer> products, String orderComments) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = countTotalPrice(products);
        this.orderStatus = OrderStatus.NEW;
        this.orderComments = orderComments;
        ID = incrementID++;
    }

    public Order(Customer customer, Map<Product, Integer> products) {
        this(customer, products, "NO ORDER COMMENTS");
    }

    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Long getID() {
        return ID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderComments() {
        return orderComments;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public static BigDecimal countTotalPrice(Map<Product, Integer> order) {
        BigDecimal result = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> product : order.entrySet()) {
            result = result.add((product.getKey().getPrice().multiply(BigDecimal.valueOf(product.getValue()))));
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "Order: [" + ID + "]\n" + customer + "\n\n"
                + orderComments + "\n\n"
                + "Status: " + orderStatus.toString() + "\n\n"
                + "\t***CART***\n";

        StringBuilder tempStrBuilder = new StringBuilder();
        for(Map.Entry<Product, Integer> product : products.entrySet()) {
            tempStrBuilder.append("Quantity: ").append(product.getValue()).append("\n").append(product.getKey()).append("\n");
        }
        result += tempStrBuilder;
        return result;
    }


}
