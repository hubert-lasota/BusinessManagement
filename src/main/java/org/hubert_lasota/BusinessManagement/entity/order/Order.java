package org.hubert_lasota.BusinessManagement.entity.order;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.entity.product.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

public class Order {
    private final Long id;
    private static long incrementID = 1;

    private final Customer customer;
    private final Map<Product, Integer> products;
    private final BigDecimal orderValue;
    private final Instant createdAt;
    private Instant updatedAt;
    private String orderComments;
    private OrderStatus orderStatus;

    public Order(Customer customer, Map<Product, Integer> products, Instant createdAt, String orderComments) {
        this.customer = customer;
        this.products = products;
        this.orderValue = countOrderValue(products);
        this.orderStatus = OrderStatus.NEW;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.orderComments = orderComments;
        id = incrementID++;
    }

    public Order(Customer customer, Map<Product, Integer> products, Instant createdAt) {
        this(customer, products, createdAt, "NO ORDER COMMENTS");
    }

    public Order(Customer customer, Map<Product, Integer> products, String orderComments) {
        this(customer, products, Instant.now(), orderComments);
    }

    public Order(Customer customer, Map<Product, Integer> products) {
        this(customer, products, Instant.now(), "NO ORDER COMMENTS");
    }

    private static BigDecimal countOrderValue(Map<Product, Integer> order) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal productPrice;
        BigDecimal multiplier;
        for (Map.Entry<Product, Integer> product : order.entrySet()) {
            multiplier = new BigDecimal(product.getValue());
            productPrice = product.getKey().getPrice();
            result = result.add(productPrice.multiply(multiplier));
        }
        return result;
    }

    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderComments() {
        return orderComments;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(products, order.products) && Objects.equals(orderValue, order.orderValue) && Objects.equals(createdAt, order.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products, orderValue, createdAt);
    }

    @Override
    public String toString() {
        return "Order{" +
                "ID=" + id +
                ", customer=" + customer +
                ", products=" + products +
                ", orderValue=" + orderValue +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", orderComments='" + orderComments + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }

}
