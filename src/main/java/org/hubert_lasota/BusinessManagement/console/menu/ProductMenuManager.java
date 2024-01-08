package org.hubert_lasota.BusinessManagement.console.menu;


import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.NoProductsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.*;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTable;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTableHeader;
import static org.hubert_lasota.BusinessManagement.console.ui.data.ProductMenuUIData.*;

public class ProductMenuManager implements Menu {
    private final ProductService productService;

    public ProductMenuManager(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(PRODUCT_MENU_TITLE, PRODUCT_MENU_CONTENT));
            int inputResult = readInt();
            switch (inputResult) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    showProductsAndOpenEditor();
                    break;
                case 4:
                    findProducts();
                    break;
                case 5:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }

        }
    }

    public void createProduct() {
        do {
            Product product = productForm();
            productService.saveProduct(product);
        } while (continueInput("Do you want to add another product yes/no?", "no"));
    }

    private Product productForm() {
        System.out.println(createTableHeader("CREATING NEW PRODUCT"));
        System.out.print("Type product's name: ");
        String name = readLine();
        System.out.print("Type product's price: ");
        BigDecimal price = readBigDecimal();
        System.out.print("Type product's description: ");
        String description = readLine();
        return new Product(name, price, description);
    }

    public void deleteProduct() {
        System.out.println(createStarBorder(PRODUCT_MENU_DELETE_CONTENT));
        try {
            showProducts();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarBorder(e.getMessage()));
            return;
        }
        Long id = readLong();
        productService.deleteProductById(id);
    }

    private void showProductsAndOpenEditor() {
        try {
            showProducts();
            openEditorOnProduct();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarBorder(e.getMessage()));
        }
    }

    public void showProducts() throws NoProductsInDatabaseException {
        List<Product> products = productService.findAllProducts();
        System.out.println(createTableHeader("PRODUCTS"));
        products.forEach(System.out::println);
    }

    private void openEditorOnProduct() {
        String operationMessage = String.join(" " ,PRODUCT_MENU_OPEN_EDITOR_MESSAGE);
        if(continueOperation(operationMessage, "no")) {
            System.out.print("Type product's ID: ");
            Long id = readLong();
            try {
                editProduct(id);
            } catch (NoSuchIdException exc) {
                System.out.println(createStarBorder(exc.getMessage()));
            }
        }
    }

    private void editProduct(Long id) throws NoSuchIdException {
        String productsName = productService.findProductById(id).getName();
        System.out.println(createTable("Edit: " + productsName, PRODUCT_MENU_EDITOR_CONTENT));

        int chosenOperation = readInt();
        switch (chosenOperation) {
            case 1:
                updateProductsName(id);
                break;
            case 2:
                updateProductsPrice(id);
                break;
            case 3:
                updateProductsDescription(id);
                break;
            case 4:
                productService.deleteProductById(id);
                break;
            case 5:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private void updateProductsName(Long id) {
        String currentName = productService.findProductById(id).getName();
        System.out.println("Current name: " + currentName);
        System.out.print("\nType new product's name: ");
        String newName = readLine();
        productService.updateProductsName(id, newName);
    }

    private void updateProductsPrice(Long id) {
        BigDecimal currentPrice = productService.findProductById(id).getPrice();
        System.out.println("Current price: " + currentPrice);
        System.out.print("\nType new product's price: ");
        BigDecimal newPrice = readBigDecimal();
        productService.updateProductsPrice(id, newPrice);
    }

    private void updateProductsDescription(Long id) {
        String currentDescription = productService.findProductById(id).getDescription();
        System.out.println("Current description: " + currentDescription);
        System.out.print("\nType new product's description: ");
        String description = readLine();
        productService.updateProductsDescription(id, description);
    }

    public void findProducts() {
        System.out.println(createTable(PRODUCT_MENU_FIND_PRODUCTS_TITLE,
                PRODUCT_MENU_FIND_PRODUCTS_CONTENT));
        int chosenOperation = readInt();
        try {
            List<Product> foundProducts = findProductsByData(chosenOperation);
            foundProducts.forEach(System.out::println);
            openEditorOnProduct();
        } catch (NoProductsInDatabaseException | NoSuchIdException exc) {
            System.out.println(createStarBorder(exc.getMessage()));
        }
    }

    private List<Product> findProductsByData(int chosenOperation)
            throws NoProductsInDatabaseException, NoSuchIdException {
        switch (chosenOperation) {
            case 1:
                return List.of(findProductById());
            case 2:
                return findProductsByName();
            case 3:
                return findProductsBetweenPrices();
            case 4:
                return findProductsByDescription();
            default:
                WrongInputException.throwAndCatchException();
        }
        throw new NoProductsInDatabaseException();
    }

    private Product findProductById() throws NoSuchIdException {
        System.out.println("Type product's ID: ");
        Long id = readLong();
        return productService.findProductById(id);
    }

    private List<Product> findProductsByName() throws NoProductsInDatabaseException {
        System.out.print("Type product's name: ");
        String name = readLine();
        return productService.findProductsByName(name);
    }

    private List<Product> findProductsBetweenPrices() throws NoProductsInDatabaseException {
        System.out.println("You need to type lower and upper price limits");
        System.out.print("Lower: ");
        BigDecimal lowerPrice = readBigDecimal();
        System.out.print("Upper: ");
        BigDecimal upperPrice = readBigDecimal();
        return productService.findProductsBetweenPrices(lowerPrice, upperPrice);
    }

    private List<Product> findProductsByDescription() throws NoProductsInDatabaseException {
        System.out.print("Type product's description: ");
        String description = readLine();
        return productService.findProductsByDescription(description);
    }

}
