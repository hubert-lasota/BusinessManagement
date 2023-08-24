package org.Business.order;

import org.Business.BusinessManagement;
import org.Business.customer.Customer;
import org.Business.customer.CustomerManagement;
import org.Business.product.Product;
import org.Business.product.ProductManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OrderManagement {
    private static final List<Order> orders = new ArrayList<>();


    private OrderManagement(){}


    public static void ordersMenu() {
        showOrdersMenuInterface();
        try(Scanner userInput = new Scanner(System.in)) {
            switch (userInput.nextInt()) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    // TODO deleteOrder
                    break;
                case 3:
                    // TODO showOrders
                    break;
                case 4:
                    BusinessManagement.homeMenu();

            }
        }
    }

    private static void createOrder() {
        CustomerManagement.showCustomers();
        Customer customer = chooseCustomer();
        ProductManagement.showProducts();
        Order order = new Order(customer, chooseProducts());
        orders.add(order);
    }

    private static Customer chooseCustomer() {
        System.out.print("Choose an customer: ");
        try(Scanner userInput = new Scanner(System.in)) {
            long id = userInput.nextLong();

            return CustomerManagement.getCustomers().stream()
                    .filter(c -> c.getID() == id)
                    .findFirst().orElseThrow();
        }
    }

    private static List<Product> chooseProducts() {
        List<Product> tempProducts = new ArrayList<>();
        String result;

        System.out.println("If you want to finish taking order press 'f'");
        System.out.println("If you want to quit taking order press 'q'");
        try(Scanner userInput = new Scanner(System.in)) {
            do {
                result = userInput.nextLine();
                if (result.equals("f")) break;
                else if (result.equals("q")) return new ArrayList<>();
                Product product = ProductManagement.getProducts().get(Integer.parseInt(result) - 1);
                tempProducts.add(product);
            } while (true);
        }
        return tempProducts;
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
