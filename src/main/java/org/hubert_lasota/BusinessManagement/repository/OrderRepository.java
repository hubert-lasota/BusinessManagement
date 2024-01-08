package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Repository<Order, Long> {

    Optional<List<Order>> findByCustomersId(Long customersId);

    Optional<List<Order>> findByStatus(OrderStatus orderStatus);

    Optional<List<Order>> findByOrderComments(String orderComments);

    Optional<List<Order>> findBetweenOrderValues(BigDecimal lowerOrderValue, BigDecimal upperOrderValue);

    Optional<List<Order>> findOrdersCreatedBetweenDates(Instant startDate, Instant endDate);

    Optional<List<Order>> findOrdersUpdatedBetweenDates(Instant startDate, Instant endDate);

}
