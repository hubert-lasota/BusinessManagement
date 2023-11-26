package org.hubert_lasota.BusinessManagement.menu;

import org.hubert_lasota.BusinessManagement.exception.NoOrdersInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoProductsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.order.Order;
import org.hubert_lasota.BusinessManagement.repository.OrderRepository;
import org.hubert_lasota.BusinessManagement.product.Product;
import org.hubert_lasota.BusinessManagement.repository.ProductRepository;

import java.util.*;

import static org.hubert_lasota.BusinessManagement.input.UserInputReader.readInt;
import static org.hubert_lasota.BusinessManagement.input.UserInputReader.readLine;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.OrderMenuUIData.*;

public class OrderMenuManager implements Menu {
    private static OrderMenuManager orderMenuManager;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ProductMenuManager productMenuManager;

    private OrderMenuManager() {
        orderRepository = OrderRepository.getInstance();
        productMenuManager = ProductMenuManager.getInstance();
        productRepository = ProductRepository.getInstance();
    }

    public static OrderMenuManager getInstance() {
        if(orderMenuManager == null) {
            orderMenuManager = new OrderMenuManager();
        }
        return orderMenuManager;
    }

    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(ORDER_MENU_TITLE, ORDER_MENU_CONTENT));

            int inputResult = readInt();
            switch (inputResult) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    deleteOrder();
                    break;
                case 3:
                    try {
                        showOrders();
                    } catch (NoOrdersInDatabaseException e) {
                        System.out.println(createStarFrame(e.getMessage()));
                    }
                    break;
                case 4:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void createOrder() {
        while (true) {
            try {
                productMenuManager.showProducts();
            } catch (NoProductsInDatabaseException e) {
                System.out.println(createStarFrame(e.getMessage()));
                return;
            }
            pickProducts();

            if (doContinueOperation("Do you want to add another order y/n?")) {
                break;
            }
        }
    }

    private Map<Product, Integer> pickProducts() {
        System.out.println(createStarFrame("Type product's ID to pick", "Type 'q' to quit"));
        String result = "";
        Long id;
        Map<Product, Integer> products = new HashMap<>();
        while (true) {
            result = readLine();
            if(result.equalsIgnoreCase("q")) {
                break;
            }
            id = Long.parseLong(result);
            putProduct(id, products);
        }
        return products;
    }

    private void putProduct(Long id, Map<Product, Integer> products) {
        System.out.println("Type quantity: ");
        Integer quantity = readInt();
        Product product = productRepository.findById(id).orElseThrow(NoSuchIdException::new);
        if (products.containsKey(product)) {
            products.compute(product, (k,v) -> v = quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public void deleteOrder() {
        while (true) {
            try {
                productMenuManager.showProducts();
            } catch (NoProductsInDatabaseException e) {
                System.out.println(createStarFrame(e.getMessage()));
                return;
            }
            System.out.println(createStarFrame("Type product's ID to pick", "Type 'q' to quit"));
            String result = readLine();
            if(!isPickedId(result)) {
                return;
            }
            Long id = Long.parseLong(result);
            orderRepository.delete(id);

            if (doContinueOperation("Do you want to delete another order y/n?")) {
                break;
            }
        }
    }

    private boolean isPickedId(String result) {
        return !result.equalsIgnoreCase("q");
    }

    private boolean doContinueOperation(String message) {
        System.out.println(createTableFrame(message));
        String continueInput = readLine();
        if(continueInput.equalsIgnoreCase("n")) {
            return false;
        }
        return true;
    }


    public void showOrders() throws NoOrdersInDatabaseException {
        System.out.println(createTitleOfTable("ORDERS"));
        List<Order> orders = orderRepository.findAll().orElseThrow(NoOrdersInDatabaseException::new);
        orders.forEach(System.out::println);
    }

}
