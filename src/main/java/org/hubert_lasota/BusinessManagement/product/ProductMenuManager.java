package org.hubert_lasota.BusinessManagement.product;


import org.hubert_lasota.BusinessManagement.exception.NoProductsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.hubert_lasota.BusinessManagement.BusinessManagementConsole.userInput;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.ProductMenuUIData.*;

public class ProductMenuManager {
    private static ProductMenuManager productMenuManager;
    private ProductRepository productRepository;


    private ProductMenuManager() {
        productRepository = ProductRepository.getInstance();
    }

    public static ProductMenuManager getInstance() {
        if(productMenuManager == null) {
            productMenuManager = new ProductMenuManager();
        }
        return productMenuManager;
    }


    public void productMenu() {
        while (true) {
            System.out.println(createTable(PRODUCT_MENU_TITLE, PRODUCT_MENU_CONTENT));
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
                    try {
                        showProducts();
                    } catch (NoProductsInDatabaseException e) {
                        System.out.println(createStarFrame(e.getMessage()));
                        break;
                    }
                    openEditorOnProduct();
                    break;
                case 4:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }

        }
    }


    public void createProduct() {
        while (true) {
            Product product = productForm();
            if (isProductPresentInDataBase(product)) {
                System.out.println(createStarFrame("This product already exist in database!"));
            } else {
                System.out.println(createStarFrame("You have successfully added new product!"));
                productRepository.save(product);
            }

            System.out.println(createTableFrame("Do you want to add another product y/n?"));
            String continueInput = userInput.nextLine();
            if (continueInput.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private Product productForm() {
        System.out.println(createTitleOfTable("CREATING NEW PRODUCT"));
        System.out.print("Type product's name: ");
        String name = userInput.nextLine();
        System.out.print("Type product's price: ");
        BigDecimal price = new BigDecimal(userInput.nextLine());
        System.out.print("Type product's description: ");
        String description = userInput.nextLine();
        return new Product(name, price, description);
    }

    private boolean isProductPresentInDataBase(Product product) {
        if (productRepository.findAll().isPresent()) {
            return productRepository.findAll().get()
                    .stream()
                    .anyMatch(p -> p.getName().equals(product.getName()));
        }
        return false;
    }

    public void deleteProduct() {
        System.out.println(createStarFrame(PRODUCT_MENU_DELETE_CONTENT));
        try {
            showProducts();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return;
        }
        Long id = userInput.nextLong();
        userInput.nextLine();
        productRepository.delete(id);
    }

    public void showProducts() throws NoProductsInDatabaseException {
        System.out.println(createTitleOfTable("PRODUCTS"));
        List<Product> products = productRepository.findAll().orElseThrow(NoProductsInDatabaseException::new);
        products.forEach(System.out::println);
    }

    public void openEditorOnProduct() {
        System.out.println(createStarFrame(PRODUCT_MENU_OPEN_EDITOR_MESSAGE));

        String inputResult = userInput.nextLine();
        if(!(inputResult.equals("q"))) {
            Long id = Long.parseLong(inputResult);
            Product product;
            try {
                product = productRepository.findById(id).orElseThrow(NoSuchIdException::new);
            } catch (NoSuchIdException exc) {
                System.out.println(createStarFrame(exc.getMessage()));
                return;
            }
            editProduct(product);
        }
    }

    private void editProduct(Product product) {
        System.out.println(createTable("Edit: " + product.getName(), PRODUCT_MENU_EDITOR_CONTENT));

        int r = userInput.nextInt();
        userInput.nextLine();
        switch (r) {
            case 1:
                System.out.print("Type new product's name: ");
                String name = userInput.nextLine();
                product.setName(name);
                break;
            case 2:
                System.out.print("Type new product's price: ");
                String price = userInput.nextLine();
                product.setPrice(new BigDecimal(price));
                break;
            case 3:
                System.out.print("Type new product's description: ");
                String description = userInput.nextLine();
                product.setDescription(description);
                break;
            case 4:
                productRepository.delete(product.getID());
        }
    }

}
