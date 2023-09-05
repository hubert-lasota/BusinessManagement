package org.Business.order;

import org.Business.customer.Customer;
import org.Business.product.Product;

import java.math.BigDecimal;
import java.util.Map;

public class Order {
    private final long ID;
    private static long incrementID = 1;

    private final Customer customer;
    private final Map<Product, Integer> products;
    private final BigDecimal totalPrice;
    private String orderComments;
    private OrderStatus orderStatus;

    public Order(Customer customer, Map<Product, Integer> products) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = countTotalPrice(products);
        this.orderStatus = OrderStatus.NEW;
        this.orderComments = "no order comments";
        ID = incrementID++;
    }

    public Order(Customer customer, Map<Product, Integer> products, String orderComments) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = countTotalPrice(products);
        this.orderStatus = OrderStatus.NEW;
        this.orderComments = orderComments;
        ID = incrementID++;
    }



    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public long getID() {
        return ID;
    }


    public static BigDecimal countTotalPrice(Map<Product, Integer> order) {
        BigDecimal result = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> product: order.entrySet()) {
            result = result.add((product.getKey().getPrice().multiply(BigDecimal.valueOf(product.getValue()))));
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "Order: " + ID + ".\n" + customer + "\n"
                + orderComments + "\n"
                + "status: " + orderStatus.toString().toLowerCase() + "\n";

        StringBuilder tempStrBuilder = new StringBuilder();
        for(Map.Entry<Product, Integer> product : products.entrySet()) {
            tempStrBuilder.append("Quantity: ").append(product.getValue()).append("\n").append(product.getKey()).append("\n");
        }
        result += tempStrBuilder;
        return result;
    }


}
