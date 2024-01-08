package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;
import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.NoOrdersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public Order saveOrder(Order order) {
        if(isOrderPresentInDataBase(order.getId())) {
            return order;
        }
        return orderRepository.save(order);
    }

    private boolean isOrderPresentInDataBase(Long id) {
        return orderRepository.findById(id).isPresent();
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomersId(customerId)
                .orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Order> findOrdersByOrderComments(String orderComments) {
        return orderRepository.findByOrderComments(orderComments)
                .orElseThrow(() -> new NoOrdersInDatabaseException("There are no orders with these order comments"));
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus)
                .orElseThrow(() -> new NoOrdersInDatabaseException("There are no orders with this status"));
    }

    @Override
    public List<Order> findOrdersBetweenOrderValues(BigDecimal lowerOrderValue, BigDecimal upperOrderValue) {
        return orderRepository.findBetweenOrderValues(lowerOrderValue, upperOrderValue)
                .orElseThrow(() -> new NoOrdersInDatabaseException("There are no orders between these order values"));
    }

    @Override
    public List<Order> findOrdersCreatedBetweenDates(Instant startDate, Instant endDate) {
        return orderRepository.findOrdersCreatedBetweenDates(startDate, endDate)
                .orElseThrow(() -> new NoOrdersInDatabaseException("There are no orders created between these dates"));
    }

    @Override
    public List<Order> findOrdersUpdatedBetweenDates(Instant startDate, Instant endDate) {
        return orderRepository.findOrdersUpdatedBetweenDates(startDate, endDate)
                .orElseThrow(() -> new NoOrdersInDatabaseException("There are no orders updated between these dates"));
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll().orElseThrow(NoOrdersInDatabaseException::new);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.delete(id);
    }

    @Override
    public void updateOrderComments(Long id, String orderComments) {
        Order orderToUpdate = findOrderById(id);
        orderToUpdate.setOrderComments(orderComments);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order orderToUpdate = findOrderById(id);
        orderToUpdate.setOrderStatus(orderStatus);
    }

    @Override
    public void putProductInCart(Long productId, Integer quantity, Map<Product, Integer> productsInCart)
            throws NoSuchIdException {
        Product product = productService.findProductById(productId);
        if(productsInCart.containsKey(product)) {
            productsInCart.compute(product, (k, v) -> v = quantity);
        }
    }

}
