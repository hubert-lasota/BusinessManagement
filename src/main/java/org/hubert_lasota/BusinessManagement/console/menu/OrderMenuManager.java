package org.hubert_lasota.BusinessManagement.console.menu;

import org.hubert_lasota.BusinessManagement.entity.customer.Customer;
import org.hubert_lasota.BusinessManagement.entity.order.Order;
import org.hubert_lasota.BusinessManagement.entity.order.OrderStatus;
import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.*;
import org.hubert_lasota.BusinessManagement.service.CustomerService;
import org.hubert_lasota.BusinessManagement.service.OrderService;
import org.hubert_lasota.BusinessManagement.service.ProductService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.*;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTable;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTableHeader;
import static org.hubert_lasota.BusinessManagement.console.ui.data.OrderMenuUIData.*;

public class OrderMenuManager implements Menu {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderMenuManager(OrderService orderService,
                            CustomerService customerService,
                            ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }


    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(ORDER_MENU_TITLE, ORDER_MENU_CONTENT));
            int chosenOperation = readInt();
            switch (chosenOperation) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    deleteOrder();
                    break;
                case 3:
                    showOrdersAndHandleException();
                    break;
                case 4:
                    findOrders();
                    break;
                case 5:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void createOrder() {
        do {
            Customer customer;
            Map<Product, Integer> productsInCart;
            try {
                customer = pickCustomer();
                List<Product> products = productService.findAllProducts();
                products.forEach(System.out::println);
                productsInCart = pickProducts();
            } catch (NoProductsInDatabaseException | NoCustomersInDatabaseException | NoSuchIdException e) {
                System.out.println(createStarBorder(e.getMessage()));
                return;
            }
            Order order = new Order(customer, productsInCart, Instant.now());
            orderService.saveOrder(order);
            System.out.println(createStarBorder("Order is successfully created!"));
        } while (continueInput("Do you want to add another order y/n?", "n"));
    }

    private Customer pickCustomer()
            throws NoCustomersInDatabaseException, NoSuchIdException {
        customerService.findAllCustomers().forEach(System.out::println);
        System.out.print("Type customer's ID: ");
        Long id = readLong();
        return customerService.findCustomerById(id);
    }

    private Map<Product, Integer> pickProducts() {
        String operationMessage = createStarBorder("Type product's ID to put product in cart", "Type 'q' to quit");
        Map<Product, Integer> productsInCart = new HashMap<>();
        System.out.println(operationMessage);
        return putProductsInCart(productsInCart);
    }

    private Map<Product, Integer> putProductsInCart(Map<Product, Integer> productsInCart)
            throws NoSuchIdException {
        Long productId;
        String userInput = readLine();
        while(!userInput.equalsIgnoreCase("q")) {
            productId = parseLongAndHandleException(userInput);
            System.out.print("Type quantity: ");
            Integer quantity = readInt();
            orderService.putProductInCart(productId, quantity, productsInCart);
        }

        return productsInCart;
    }

    private Long parseLongAndHandleException(String number) {
        while (true) {
            try {
                return Long.parseLong(number);
            } catch (NumberFormatException exc) {
                WrongInputException.throwAndCatchException();
            }
        }
    }

    public void deleteOrder() {
        do {
            try {
                List<Product> products = productService.findAllProducts();
                products.forEach(System.out::println);
            } catch (NoProductsInDatabaseException e) {
                System.out.println(createStarBorder(e.getMessage()));
                return;
            }
            System.out.println("Type product's ID to delete:" );
            Long id = readLong();
            orderService.deleteOrderById(id);
        } while (continueInput("Do you want to delete another order y/n: ", "n"));
    }

    private void showOrdersAndHandleException() {
        try {
            showOrders();
        } catch (NoOrdersInDatabaseException exc) {
            System.out.println(createStarBorder(exc.getMessage()));
        }
    }

    public void showOrders() throws NoOrdersInDatabaseException {
        System.out.println(createTableHeader("ORDERS"));
        List<Order> orders = orderService.findAllOrders();
        orders.forEach(System.out::println);
        openEditorOnOrder();
    }

    public void findOrders() {
        System.out.println(createTable(ORDER_MENU_FIND_ORDERS_TITLE,
                ORDER_MENU_FIND_ORDERS_CONTENT));
        int chosenOperation = readInt();
        try {
            List<Order> foundOrders = findOrdersByData(chosenOperation);
            foundOrders.forEach(System.out::println);
            openEditorOnOrder();
        } catch (NoSuchIdException | NoOrdersInDatabaseException | NoCustomersInDatabaseException e) {
            System.out.println(createStarBorder(e.getMessage()));
        }
    }

    private List<Order> findOrdersByData(int chosenOperation)
            throws NoSuchIdException, NoOrdersInDatabaseException, NoCustomersInDatabaseException {
        switch (chosenOperation) {
            case 1:
                return List.of(findOrderById());
            case 2:
                return findOrdersByCustomer();
            case 3:
                return findOrdersBetweenOrderValues();
            case 4:
                return findOrdersCreatedBetweenDates();
            case 5:
                return findOrdersUpdatedBetweenDates();
            case 6:
                return findOrdersByOrderComments();
            case 7:
                return findOrdersByOrderStatus();
            default:
                WrongInputException.throwAndCatchException();
        }

        throw new NoOrdersInDatabaseException();
    }

    private Order findOrderById() throws NoSuchIdException {
        System.out.print("Type order's ID: ");
        Long id = readLong();
        return orderService.findOrderById(id);
    }

    private List<Order> findOrdersByCustomer() throws NoSuchIdException, NoCustomersInDatabaseException {
        Long customerId = pickCustomer().getId();
        return orderService.findOrdersByCustomerId(customerId);
    }

    private List<Order> findOrdersBetweenOrderValues() throws NoOrdersInDatabaseException {
        System.out.println("You need to type lower and upper order value");
        System.out.print("Lower: ");
        BigDecimal lowerOrderValue = readBigDecimal();
        System.out.print("Upper: ");
        BigDecimal upperOrderValue = readBigDecimal();

        return orderService.findOrdersBetweenOrderValues(lowerOrderValue, upperOrderValue);
    }

    private List<Order> findOrdersCreatedBetweenDates() throws NoOrdersInDatabaseException {
        System.out.println("You need to type start and end date in format: yyyy-MM-dd");
        System.out.println("For example: 2001-01-01");

        List<Instant> startAndEndInstant = readUsersStartAndEndInstant();
        Instant start = startAndEndInstant.get(0);
        Instant end = startAndEndInstant.get(1);
        return orderService.findOrdersCreatedBetweenDates(start, end);
    }

    private List<Order> findOrdersUpdatedBetweenDates() throws NoOrdersInDatabaseException {
        System.out.println("You need to type start and end date in format: yyyy-MM-dd");
        System.out.println("For example: 2001-01-01");

        List<Instant> startAndEndInstant = readUsersStartAndEndInstant();
        Instant start = startAndEndInstant.get(0);
        Instant end = startAndEndInstant.get(1);
        return orderService.findOrdersUpdatedBetweenDates(start, end);
    }

    private List<Instant> readUsersStartAndEndInstant() {
        System.out.print("Type start date: ");
        LocalDate start = readLocalDate();
        System.out.print("Type end date: ");
        LocalDate end = readLocalDate();

        Instant instantStart = start.atStartOfDay(ZoneId.of("+0")).toInstant();
        Instant instantEnd = end.atStartOfDay(ZoneId.of("+0")).toInstant();
        return List.of(instantStart, instantEnd);
    }

    private List<Order> findOrdersByOrderComments() throws NoOrdersInDatabaseException {
        System.out.print("Type order's comments: ");
        String orderComments = readLine();
        return orderService.findOrdersByOrderComments(orderComments);
    }

    private List<Order> findOrdersByOrderStatus() throws NoOrdersInDatabaseException {
        OrderStatus chosenStatus = chooseOrderStatus();
        switch (chosenStatus) {
            case NEW:
                return orderService.findOrdersByStatus(OrderStatus.NEW);
            case IN_PROGRESS:
                return orderService.findOrdersByStatus(OrderStatus.IN_PROGRESS);
            case COMPLETED:
                return orderService.findOrdersByStatus(OrderStatus.COMPLETED);
            default:
                WrongInputException.throwAndCatchException();
        }

        throw new NoOrdersInDatabaseException();
    }

    private OrderStatus chooseOrderStatus() {
        System.out.print("Choose order status");
        System.out.print("1. " + OrderStatus.NEW + "\t");
        System.out.print("2. " + OrderStatus.IN_PROGRESS + "\t");
        System.out.println("3. " + OrderStatus.COMPLETED + "\t");

        int chosenStatus = readInt();
        switch (chosenStatus) {
            case 1:
                return OrderStatus.NEW;
            case 2:
                return OrderStatus.IN_PROGRESS;
            case 3:
                return OrderStatus.COMPLETED;
            default:
                WrongInputException.throwAndCatchException();
        }
        throw new NoOrdersInDatabaseException();
    }

    private void openEditorOnOrder() {
        String operationMessage = String.join(" ", ORDER_MENU_OPEN_EDITOR_MESSAGE);
        if(continueOperation(operationMessage, "no")) {
            System.out.print("Type order's ID: ");
            Long id = readLong();
            try {
                editOrder(id);
            } catch (NoSuchIdException exc) {
                System.out.println(createStarBorder(exc.getMessage()));
            }
        }
    }

    private void editOrder(Long id) throws NoSuchIdException {
        Long ordersId = orderService.findOrderById(id).getId();
        System.out.println(createTable("Edit: " + ordersId, ORDER_MENU_EDITOR_CONTENT));

        int chosenOperation = readInt();
        switch (chosenOperation) {
            case 1:
                updateOrderStatus(id);
                break;
            case 2:
                updateOrderComments(id);
                break;
            case 3:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private void updateOrderComments(Long id) {
        String currentOrderComments = orderService.findOrderById(id).getOrderComments();
        System.out.println("Current order comments: " + currentOrderComments);
        System.out.print("\nType new order comments: ");
        String newOrderComments = readLine();
        orderService.updateOrderComments(id, newOrderComments);
    }

    private void updateOrderStatus(Long id) {
        OrderStatus currentOrderStatus = orderService.findOrderById(id).getOrderStatus();
        System.out.println("Current order status: " + currentOrderStatus);
        System.out.print("\nChoose new order status: ");
        OrderStatus newOrderStatus = chooseOrderStatus();
        orderService.updateOrderStatus(id, newOrderStatus);
    }

}
