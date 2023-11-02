package org.hubert_lasota.BusinessManagement.order;

import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderRepository {
    private static OrderRepository orderRepository;
    private Map<Long, Order> orders;


    private OrderRepository() {
        orders = new HashMap<>();
    }

    public static OrderRepository getInstance() {
        if(orderRepository == null) {
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }


    public Order save(Order order) {
        return orders.putIfAbsent(order.getID(), order);
    }


    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Optional<List<Order>> findByData(String data, Function<Order, String> fieldExtractor) {
        List<Order> tempList = orders.values().stream()
                .filter(o -> fieldExtractor.apply(o).contains(data))
                .collect(Collectors.toList());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    public Optional<List<Order>> findAll() {
      List<Order> tempList = List.copyOf(orders.values());
      return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    public Order update(Long id, Order order) {
        Order tempOrder = findById(id).orElseThrow(NoSuchIdException::new);
        return update(tempOrder, order);
    }

    private Order update(Order orderToUpdate, Order orderUpdater) {
        orderToUpdate.setProducts(orderUpdater.getProducts());
        orderToUpdate.setOrderStatus(orderUpdater.getOrderStatus());
        orderToUpdate.setOrderComments(orderUpdater.getOrderComments());
        orderToUpdate.setTotalPrice(orderUpdater.getTotalPrice());
        return orderToUpdate;
    }

    public void delete(Long id) {
        Order order = findById(id).orElseThrow(NoSuchIdException::new);
        orders.remove(order.getID(), order);
    }
}
