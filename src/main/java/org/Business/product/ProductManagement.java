package org.Business.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    private static final List<Product> products = new ArrayList<>();


    private ProductManagement(){}


    public static void productsMenu() {
        showProductsMenuInterface();
    }

    public static void showProducts() {
        for(Product product : products) {
            System.out.println(product + "\n");
        }
    }

    public static List<Product> getProducts() {
        return products;
    }


    private static void showProductsMenuInterface() {
        System.out.println("=".repeat(30));
        System.out.println("\t***PRODUCTS***");
        System.out.println("1. Create product");
        System.out.println("2. Delete product");
        System.out.println("3. Show products");
        System.out.println("4. Go back to home menu");
    }

}
