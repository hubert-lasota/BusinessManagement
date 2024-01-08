package org.hubert_lasota.BusinessManagement.service;


import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;
import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Order saveOrder(Order order);

    Order findOrderById(Long id);

    List<Order> findOrdersByCustomerId(Long customerId);

    List<Order> findOrdersByStatus(OrderStatus orderStatus);

    List<Order> findOrdersByOrderComments(String orderComments);

    List<Order> findOrdersBetweenOrderValues(BigDecimal lowerOrderValue, BigDecimal upperOrderValue);

    List<Order> findOrdersCreatedBetweenDates(Instant startDate, Instant endDate);

    List<Order> findOrdersUpdatedBetweenDates(Instant startDate, Instant endDate);

    List<Order> findAllOrders();

    void updateOrderComments(Long id, String orderComments);

    void updateOrderStatus(Long id, OrderStatus orderStatus);

    void deleteOrderById(Long id);

    void putProductInCart(Long productId,
                          Integer quantity,
                          Map<Product, Integer> productsInCart) throws NoSuchIdException;

}
