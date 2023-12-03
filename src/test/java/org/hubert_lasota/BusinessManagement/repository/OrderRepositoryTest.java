package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.hubert_lasota.BusinessManagement.order.Order;
import org.hubert_lasota.BusinessManagement.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRepositoryTest {
    OrderRepository orderRepository = OrderRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = OrderRepository.class.getDeclaredField("orders");
        field.setAccessible(true);
        Map<Long, Order> orders = (Map<Long, Order>) field.get(orderRepository);
        orders.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = orderRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();
        for(int i = 0; i < counter; i++) {
            orderRepository.save(new Order(customer, products));
        }
        long repositoryCounter = orderRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveOrder() {
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();
        Order actual = new Order(customer, products);
        long repositorySizeBeforeSave = orderRepository.count();

        Order expected = orderRepository.save(actual);
        long repositorySizeAfterSave = orderRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleOrders() {
        int counter = 10;
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();

        for(int i = 0; i < counter; i++) {
            orderRepository.save(new Order(customer, products));
        }

        long repositorySizeAfterSave = orderRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteOrder() {
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();
        Order order = new Order(customer, products);
        orderRepository.save(order);
        long repositorySizeAfterSave = orderRepository.count();

        orderRepository.delete(order.getID());
        long repositorySizeAfterDelete = orderRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleOrders() {
        int counter = 10;
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();
        Order[] orders = new Order[counter];

        for(int i = 0; i < counter; i++) {
            orders[i] = orderRepository.save(new Order(customer, products));
        }
        long repositorySizeAfterSave = orderRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            orderRepository.delete(orders[i].getID());
        }
        long repositorySizeAfterDelete = orderRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();
        Order order = new Order(customer, products);
        orderRepository.save(order);

        Order foundOrders = orderRepository.findById(order.getID()).get();

        assertEquals(order, foundOrders);
    }

    @Test
    void shouldNotFindById() {
        Optional<Order> foundOrder = orderRepository.findById(-1L);
        assertEquals(Optional.empty(), foundOrder);
    }

    @Test
    void shouldFindByData() {
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();

        Order order1 = new Order(customer, products);
        Order order2 = new Order(customer, products);
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> foundOrders = orderRepository.findByData("test", o -> o.getCustomer().getName()).get();

        assertEquals(order1, foundOrders.get(0));
        assertEquals(order2, foundOrders.get(1));
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<Order>> foundOrder = orderRepository.findByData("This data doesn't exists", Order::getOrderComments);
        assertEquals(Optional.empty(), foundOrder);
    }

    @Test
    void shouldFindAll() {
        Customer customer = new Customer(
                "test", "swn", "00-000", "city", "country");
        Map<Product, Integer> products = new HashMap<>();

        Order order1 = new Order(customer, products);
        Order order2 = new Order(customer, products);
        orderRepository.save(order1);
        orderRepository.save(order2);
        long repositorySizeAfterSave = orderRepository.count();

        List<Order> foundOrders = orderRepository.findAll().get();
        long foundOrdersSize = foundOrders.size();

        assertEquals(repositorySizeAfterSave, foundOrdersSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Order>> foundOrders = orderRepository.findAll();
        assertEquals(Optional.empty(), foundOrders);
    }
}
