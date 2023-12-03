package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.order.Order;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderRepository implements Repository<Order, Long> {
    private static OrderRepository orderRepository;
    private final Map<Long, Order> orders;


    private OrderRepository() {
        orders = new HashMap<>();
    }

    public static OrderRepository getInstance() {
        if(orderRepository == null) {
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }

    @Override
    public Order save(Order order) {
        orders.putIfAbsent(order.getID(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Optional<List<Order>> findByData(String data, Function<Order, String> fieldExtractor) {
        List<Order> tempList = orders.values().stream()
                .filter(o -> fieldExtractor.apply(o).contains(data))
                .collect(Collectors.toList());

        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<Order>> findAll() {
      List<Order> tempList = List.copyOf(orders.values());
      return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public void delete(Long id) {
        Order order = findById(id).orElseThrow(NoSuchIdException::new);
        orders.remove(order.getID(), order);
    }

    @Override
    public Long count() {
        return (long) orders.size();
    }
}
