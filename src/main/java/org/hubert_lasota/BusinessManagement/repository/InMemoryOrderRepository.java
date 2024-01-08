package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryOrderRepository implements OrderRepository {
    private static InMemoryOrderRepository inMemoryOrderRepository;
    private final Map<Long, Order> orders;


    private InMemoryOrderRepository() {
        orders = new HashMap<>();
    }

    public static InMemoryOrderRepository getInstance() {
        if(inMemoryOrderRepository == null) {
            inMemoryOrderRepository = new InMemoryOrderRepository();
        }
        return inMemoryOrderRepository;
    }

    @Override
    public Order save(Order order) {
        orders.putIfAbsent(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public Optional<List<Order>> findByCustomersId(Long customersID) {
        List<Order> foundOrders = orders.values()
                .stream()
                .filter(o -> o.getCustomer().getId().equals(customersID))
                .collect(Collectors.toList());

        return foundOrders.isEmpty() ? Optional.empty() : Optional.of(foundOrders);
    }

    @Override
    public Optional<List<Order>> findByStatus(OrderStatus orderStatus) {
        List<Order> foundOrders = orders.values()
                .stream()
                .filter(o -> o.getOrderStatus().equals(orderStatus))
                .collect(Collectors.toList());

        return foundOrders.isEmpty() ? Optional.empty() : Optional.of(foundOrders);
    }

    @Override
    public Optional<List<Order>> findByOrderComments(String orderComments) {
        return findByData(orderComments, Order::getOrderComments);
    }

    private Optional<List<Order>> findByData(String data, Function<Order, String> fieldExtractor) {
        List<Order> foundOrders = orders.values()
                .stream()
                .filter(o -> fieldExtractor.apply(o).contains(data))
                .collect(Collectors.toList());

        return foundOrders.isEmpty() ? Optional.empty() : Optional.of(foundOrders);
    }

    @Override
    public Optional<List<Order>> findBetweenOrderValues(BigDecimal lowerOrderValue, BigDecimal upperOrderValue) {
        return findOrdersBetweenComparableData(lowerOrderValue, upperOrderValue, Order::getOrderValue);
    }

    @Override
    public Optional<List<Order>> findOrdersCreatedBetweenDates(Instant startDate, Instant endDate) {
        return findOrdersBetweenComparableData(startDate, endDate, Order::getCreatedAt);
    }

    @Override
    public Optional<List<Order>> findOrdersUpdatedBetweenDates(Instant startDate, Instant endDate) {
        return findOrdersBetweenComparableData(startDate, endDate, Order::getUpdatedAt);
    }

    private <T extends Comparable<T>> Optional<List<Order>> findOrdersBetweenComparableData(
           T start, T end, Function<Order, T> fieldExtractor ) {
        List<Order> ordersBetweenData = orders.values()
                .stream()
                .filter(p -> fieldExtractor.apply(p).compareTo(start) >= 0)
                .filter(p -> fieldExtractor.apply(p).compareTo(end) <= 0)
                .collect(Collectors.toList());

        return ordersBetweenData.isEmpty() ? Optional.empty() : Optional.of(ordersBetweenData);
    }

    @Override
    public Optional<List<Order>> findAll() {
      List<Order> foundOrders = List.copyOf(orders.values());
      return foundOrders.isEmpty() ? Optional.empty() : Optional.of(foundOrders);
    }

    @Override
    public void delete(Long id) {
        Order order = findById(id).orElseThrow(NoSuchIdException::new);
        orders.remove(id, order);
    }

    @Override
    public Long count() {
        return (long) orders.size();
    }

}
