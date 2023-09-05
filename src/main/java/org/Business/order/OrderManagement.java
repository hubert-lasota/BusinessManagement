package org.Business.order;

import org.Business.BusinessManagement;
import org.Business.customer.Customer;
import org.Business.customer.CustomerManagement;
import org.Business.product.Product;
import org.Business.product.ProductManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.Business.BusinessManagement.userInput;
import static org.Business.order.Order.countTotalPrice;
import static org.Business.product.ProductManagement.getIndexOfProduct;
import static org.Business.product.ProductManagement.getProducts;

public class OrderManagement {
    private static final List<Order> orders = new ArrayList<>();


    private OrderManagement(){}


    public static void ordersMenu() {
        showOrdersMenuInterface();
        int inputResult = userInput.nextInt();
        userInput.nextLine();
        switch (inputResult) {
            case 1:
                createOrder();
                break;
            case 2:
                deleteOrder();
                break;
            case 3:
                showOrders();
                break;
            case 4:
                BusinessManagement.homeMenu();

        }
    }

    public static void addOrders(Order... tempOrders) {
        for(Order order : tempOrders) {
            if(!orders.contains(order)) orders.add(order);
        }
    }

    public static void showOrders() {
        if(orders.isEmpty()) System.out.println("There are no orders!");
        else {
            for (Order order : orders) {
                System.out.println("-".repeat(30).concat("\n"));
                System.out.println(order);
                System.out.println("-".repeat(30).concat("\n"));
            }
        }
        ordersMenu();
    }

    public static void deleteOrder() {
        showOrders();
        System.out.print("Type order's ID you want to delete: ");
        long id = userInput.nextLong();
        userInput.nextLine(); // cleans the buffer

        deleteThisOrder(id);
        ordersMenu();
    }

    private static void openEditorOnOrder() {
        System.out.println("=".repeat(30));
        System.out.println("If you want to open editor on order:");
        System.out.println("Type order's ID");
        System.out.println("If you want to quit. Type 'q'");
        System.out.println("=".repeat(30).concat("\n"));
    }

    private static void deleteThisOrder(long id) {
        System.out.println("Are you sure you want to delete this order? Type yes/no.");
        orders.stream()
                .filter(o -> o.getID() == id)
                .forEach(System.out::println);

        if(userInput.nextLine().equals("yes")) {
            if (orders.removeIf(o -> o.getID() == id)) {
                System.out.println("Order successfully removed from database!");
            } else {
                System.out.println("ID is incorrect!");
            }
        }
    }

    public static void createOrderForThisCustomer(Customer customer) {
        ProductManagement.showProducts();
        Map<Product, Integer> chosenProducts = chooseProducts();
        if(chosenProducts.isEmpty()) return;
        Order order = new Order(customer, chosenProducts);
        orders.add(order);
    }

    private static void createOrder() {
        CustomerManagement.showCustomers();
        Customer customer = chooseCustomer();
        createOrderForThisCustomer(customer);
        ordersMenu();
    }


    private static Customer chooseCustomer() {
        System.out.print("Choose an customer: ");
        long id = userInput.nextLong();
        userInput.nextLine(); // cleans the buffer

        return CustomerManagement.getCustomers().stream()
                .filter(c -> c.getID() == id)
                .findFirst().orElseThrow();
    }

    private static Map<Product, Integer> chooseProducts() {
        Map<Product, Integer> tempProducts = new HashMap<>();
        String result;
        long productID;
        Product product;
        int quantity;

        System.out.println("=".repeat(30));
        System.out.println("If you want to finish taking order press 'f'");
        System.out.println("If you want to cancel the order press 'q'");
        System.out.println("=".repeat(30));
        do {
            result = userInput.nextLine();

            if (result.equals("f")) break;
            else if (result.equals("q")) return new HashMap<>();

            productID = Integer.parseInt(result);
            product = getProducts().get(getIndexOfProduct(productID));
            quantity = chooseQuantity();
            if(!tempProducts.containsKey(product)){
                tempProducts.put(product, quantity);
            } else {
                int finalQuantity = quantity;
                tempProducts.computeIfPresent(product, (p, i) -> finalQuantity);
            }
            showThisOrdersCart(tempProducts);
        } while (true);

        System.out.println("Your order successfully has been created!");
        System.out.println("Total price: " + countTotalPrice(tempProducts));
        return tempProducts;
    }



    private static int chooseQuantity() {
        System.out.println("Choose quantity: ");
        int result = userInput.nextInt();
        userInput.nextLine(); // cleans the buffer
        return result;
    }
    private static void showThisOrdersCart(Map<Product, Integer> products) {
        System.out.println("-".repeat(30) + "\n");
        for (Map.Entry<Product, Integer> product: products.entrySet()) {
            System.out.println("quantity: " + product.getValue() + "\n" + product.getKey() + "\n");
        }
        System.out.println("-".repeat(30) + "\n");
    }

    private static void showOrdersMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***ORDERS***");
        System.out.println("1. Create an order");
        System.out.println("2. Delete an order");
        System.out.println("3. Show orders");
        System.out.println("4. Go back to home menu");
        System.out.println("=".repeat(30) + "\n\n");
    }
}
