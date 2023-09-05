package org.Business.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.Business.BusinessManagement.homeMenu;
import static org.Business.BusinessManagement.userInput;

public class ProductManagement {
    private static final List<Product> products = new ArrayList<>();


    private ProductManagement(){}


    public static void productsMenu() {
        showProductsMenuInterface();
        int inputResult = userInput.nextInt();
        userInput.nextLine();
        switch (inputResult) {
            case 1:
                createProduct();
                break;
            case 2:
                deleteProduct();
                break;
            case 3:
                showProducts();
                openEditorOnProduct();
                break;
            case 4:
                homeMenu();

        }

    }

    public static void openEditorOnProduct() {
        System.out.println("If you want to open editor on product:");
        System.out.println("Type product's ID");
        System.out.println("If you want to quit. Type 'q'");

        String inputResult = userInput.nextLine();
        if(inputResult.equals("q")) {
            productsMenu();
        } else {
            showOpenEditorOnProductInterface();
            int result = userInput.nextInt();
            userInput.nextLine();
            switch (result) {
                case 1:
                    System.out.print("Type new product's name: ");
                    String name = userInput.nextLine();
                    products.get(getIndexOfProduct(Long.parseLong(inputResult))).setName(name);
                    break;
                case 2:
                    System.out.print("Type new product's price: ");
                    String price = userInput.nextLine();
                    products.get(getIndexOfProduct(Long.parseLong(inputResult))).setPrice(new BigDecimal(price));
                    break;
                case 3:
                    System.out.print("Type new product's description: ");
                    String description = userInput.nextLine();
                    products.get(getIndexOfProduct(Long.parseLong(inputResult))).setDescription(description);
                    break;
                case 4:
                    deleteThisProduct(Long.parseLong(inputResult));
            }
            productsMenu();
        }
    }


    private static void deleteThisProduct(long id) {
        Product product = products.get(getIndexOfProduct(id));
        System.out.println("Are you sure you want to remove: " + product);
        System.out.print("\nType yes/no: ");
        String inputResult = userInput.nextLine();
        if(inputResult.equals("yes")) {
            products.remove(product);
            System.out.println("Product successfully removed from database!");
        }
    }
    private static void showOpenEditorOnProductInterface() {
        System.out.println("=".repeat(30));
        System.out.println("1. Change product's name");
        System.out.println("2. Change product's price");
        System.out.println("3. Change product's description");
        System.out.println("4. Delete this product");
        System.out.println("5. Go back to product's menu");
        System.out.println("=".repeat(30));
    }


    private static void deleteProduct() {
        showProducts();
        System.out.print("Type product's ID you want to delete: ");
        long inputResult = userInput.nextInt();
        userInput.nextLine();
        if(products.removeIf(p -> p.getID() == inputResult)) {
            System.out.println("Product successfully removed from database!");
        }
        else {
            System.out.println("You typed wrong ID!");
        }
    }


    public static void addProducts(Product... tempProducts) {
        for(Product product : tempProducts) {
            if(!products.contains(product)) products.add(product);
        }
    }

    public static int getIndexOfProduct(long id) {
        Product product = products.stream()
                .filter(p -> p.getID() == id)
                .findFirst().get();
        return products.indexOf(product);
    }

    public static void showProducts() {
        System.out.println("*".repeat(30));
        for(Product product : products) {
            System.out.println(product + "\n");
        }
        System.out.println("*".repeat(30).concat("\n"));
    }

    public static List<Product> getProducts() {
        return products;
    }


    private static void createProduct() {
        System.out.print("Type product'ss name: ");
        String name = userInput.nextLine();
        System.out.print("Type product's price: ");
        BigDecimal price = new BigDecimal(userInput.nextLine());
        System.out.print("Type product's description: ");
        String description = userInput.nextLine();
        Product product = new Product(name, price, description);
        if(products.contains(product)) {
            System.out.println("This product already exists in database!\n");
        } else {
            products.add(product);
            System.out.println("Product successfully created!\n");
        }
        productsMenu();
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
