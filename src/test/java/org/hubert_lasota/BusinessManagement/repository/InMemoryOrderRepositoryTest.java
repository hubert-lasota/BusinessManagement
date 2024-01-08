package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;
import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryOrderRepositoryTest {
    InMemoryOrderRepository inMemoryOrderRepository = InMemoryOrderRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryOrderRepository.class.getDeclaredField("orders");
        field.setAccessible(true);
        Map<Long, Order> orders = (Map<Long, Order>) field.get(inMemoryOrderRepository);
        orders.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = inMemoryOrderRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        for(int i = 0; i < counter; i++) {
            inMemoryOrderRepository.save(new Order(customer, products));
        }
        long repositoryCounter = inMemoryOrderRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveOrder() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order actual = new Order(customer, products);
        long repositorySizeBeforeSave = inMemoryOrderRepository.count();

        Order expected = inMemoryOrderRepository.save(actual);
        long repositorySizeAfterSave = inMemoryOrderRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleOrders() {
        int counter = 10;
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();

        for(int i = 0; i < counter; i++) {
            inMemoryOrderRepository.save(new Order(customer, products));
        }

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteOrder() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order = new Order(customer, products);
        inMemoryOrderRepository.save(order);
        long repositorySizeAfterSave = inMemoryOrderRepository.count();

        inMemoryOrderRepository.delete(order.getId());
        long repositorySizeAfterDelete = inMemoryOrderRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleOrders() {
        int counter = 10;
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order[] orders = new Order[counter];

        for(int i = 0; i < counter; i++) {
            orders[i] = inMemoryOrderRepository.save(new Order(customer, products));
        }
        long repositorySizeAfterSave = inMemoryOrderRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryOrderRepository.delete(orders[i].getId());
        }
        long repositorySizeAfterDelete = inMemoryOrderRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order = new Order(customer, products);
        inMemoryOrderRepository.save(order);

        Order foundOrders = inMemoryOrderRepository.findById(order.getId()).get();

        assertEquals(order, foundOrders);
    }

    @Test
    void shouldNotFindById() {
        Optional<Order> foundOrder = inMemoryOrderRepository.findById(-1L);
        assertEquals(Optional.empty(), foundOrder);
    }

    @Test
    void shouldFindAll() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order1 = new Order(customer, products);
        Order order2 = new Order(customer, products);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findAll().get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findAll();
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindByCustomerId() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order1 = new Order(customer, products);
        Order order2 = new Order(customer, products);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findByCustomersId(customer.getId()).get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindByCustomerId() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findByCustomersId(-1L);
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindByStatus() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order1 = new Order(customer, products);
        order1.setOrderStatus(OrderStatus.COMPLETED);

        Order order2 = new Order(customer, products);
        order2.setOrderStatus(OrderStatus.COMPLETED);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findByStatus(OrderStatus.COMPLETED).get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindByStatus() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findByStatus(OrderStatus.IN_PROGRESS);
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindByOrderComments() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();
        Order order1 = new Order(customer, products, "add extra bags");
        Order order2 = new Order(customer, products, "add extra bags");
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findByOrderComments("add extra bags").get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindByOrderComments() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findByOrderComments("empty order comments");
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindBetweenOrderValues() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products1 = new HashMap<>();
        products1.put(new Product("pencil", BigDecimal.ONE), 1);
        Order order1 = new Order(customer, products1);

        Map<Product, Integer> products2 = new HashMap<>();
        products2.put(new Product("paper", new BigDecimal("2.5")), 4);
        Order order2 = new Order(customer, products2);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findBetweenOrderValues(
                BigDecimal.ONE, new BigDecimal("10.00")).get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindBetweenOrderValues() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findBetweenOrderValues(
                BigDecimal.ZERO, BigDecimal.ONE);
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindOrdersCreatedBetweenDates() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();

        Instant date1 = LocalDateTime.of(2020, 1, 1, 12, 0, 0)
                .toInstant(ZoneOffset.of("+0"));
        Instant date2 = LocalDateTime.of(2022, 1, 1, 12, 0, 0)
                .toInstant(ZoneOffset.of("+0"));
        Order order1 = new Order(customer, products, date1);
        Order order2 = new Order(customer, products, date2);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findOrdersCreatedBetweenDates(date1, date2).get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindOrdersCreatedBetweenDates() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findOrdersCreatedBetweenDates(Instant.now(), Instant.now());
        assertEquals(Optional.empty(), foundOrders);
    }

    @Test
    void shouldFindOrdersUpdatedBetweenDates() {
        Customer customer = new Customer(
                "test", 0L);
        Map<Product, Integer> products = new HashMap<>();

        Instant date1 = LocalDateTime.of(2020, 1, 1, 12, 0, 0)
                .toInstant(ZoneOffset.of("+0"));
        Instant date2 = LocalDateTime.of(2022, 1, 1, 12, 0, 0)
                .toInstant(ZoneOffset.of("+0"));
        Order order1 = new Order(customer, products);
        order1.setUpdatedAt(date1);
        Order order2 = new Order(customer, products);
        order2.setUpdatedAt(date2);
        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);

        long repositorySizeAfterSave = inMemoryOrderRepository.count();
        List<Order> foundOrders = inMemoryOrderRepository.findOrdersUpdatedBetweenDates(date1, date2).get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindOrdersUpdatedBetweenDates() {
        Optional<List<Order>> foundOrders = inMemoryOrderRepository.findOrdersUpdatedBetweenDates(Instant.now(), Instant.now());
        assertEquals(Optional.empty(), foundOrders);
    }

}
