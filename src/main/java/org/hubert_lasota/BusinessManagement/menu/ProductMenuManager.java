package org.hubert_lasota.BusinessManagement.menu;


import org.hubert_lasota.BusinessManagement.exception.NoProductsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.product.Product;
import org.hubert_lasota.BusinessManagement.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hubert_lasota.BusinessManagement.reader.Reader.*;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.ProductMenuUIData.*;

public class ProductMenuManager implements Menu {
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
                    try {
                        showProducts();
                    } catch (NoProductsInDatabaseException e) {
                        System.out.println(createStarFrame(e.getMessage()));
                        break;
                    }
                    openEditorOnProduct();
                    break;
                case 4:
                    if(findProducts()) {
                        openEditorOnProduct();
                    }                  
                    break;
                case 5:
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
            String continueInput = readLine();
            if (continueInput.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private Product productForm() {
        System.out.println(createTitleOfTable("CREATING NEW PRODUCT"));
        System.out.print("Type product's name: ");
        String name = readLine();
        System.out.print("Type product's price: ");
        BigDecimal price = new BigDecimal(readLine());
        System.out.print("Type product's description: ");
        String description = readLine();
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
        Long id = readLong();
        productRepository.delete(id);
    }


    public void showProducts() {
        System.out.println(createTitleOfTable("PRODUCTS"));
        List<Product> products = productRepository.findAll().orElseThrow(NoProductsInDatabaseException::new);
        products.forEach(System.out::println);
    }

    public void openEditorOnProduct() {
        System.out.println(createStarFrame(PRODUCT_MENU_OPEN_EDITOR_MESSAGE));

        String inputResult = readLine();
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

    private void editProduct(Product productToUpdate) {
        System.out.println(createTable("Edit: " + productToUpdate.getName(), PRODUCT_MENU_EDITOR_CONTENT));

        int inputResult = readInt();
        switch (inputResult) {
            case 1:
                updateProductsName(productToUpdate);
                break;
            case 2:
                updateProductsPrice(productToUpdate);
                break;
            case 3:
                updateProductsDescription(productToUpdate);
                break;
            case 4:
                productRepository.delete(productToUpdate.getID());
                break;
            case 5:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private void updateProductsName(Product productToUpdate) {
        System.out.print("Type new product's name: ");
        String name = readLine();
        productToUpdate.setName(name);
    }

    private void updateProductsPrice(Product productToUpdate) {
        System.out.print("Type new product's price: ");
        String price = readLine();
        productToUpdate.setPrice(new BigDecimal(price));
    }

    private void updateProductsDescription(Product productToUpdate) {
        System.out.print("Type new product's description: ");
        String description = readLine();
        productToUpdate.setDescription(description);
    }

    public boolean findProducts() {
        System.out.println(createTable(PRODUCT_MENU_FIND_PRODUCTS_TITLE,
                PRODUCT_MENU_FIND_PRODUCTS_CONTENT));
        int result = readInt();
        List<Product> foundProducts = findProductsByData(result);

        if (!foundProducts.isEmpty()) {
            foundProducts.forEach(System.out::println);
            return true;
        }
        return false;
    }

    private List<Product> findProductsByData(int result) {
        switch (result) {
            case 1:
                return findProductsByNameAndHandleException();
            case 2:
                return findProductsByPriceAndHandleException();
            case 3:
                return findProductsByDescriptionAndHandleException();
            default:
                WrongInputException.throwAndCatchException();
        }
        return Collections.emptyList();
    }

    private List<Product> findProductsByNameAndHandleException() {
        try {
            return findProductsByName();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return Collections.emptyList();
        }
    }

    private List<Product> findProductsByName() {
        System.out.print("Type product's name: ");
        String name = readLine();
        return productRepository.findByData(name, Product::getName)
                .orElseThrow(() -> new NoProductsInDatabaseException("There are no products with this name"));
    }

    private List<Product> findProductsByPriceAndHandleException() {
        try {
            return findProductsByPrice();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return Collections.emptyList();
        }
    }

    private List<Product> findProductsByPrice() {
        System.out.println("You need to type lower and upper price limits");
        System.out.print("Lower: ");
        BigDecimal lowerPrice = new BigDecimal(readLine());
        System.out.print("Upper: ");
        BigDecimal upperPrice = new BigDecimal(readLine());
        List<Product> products = findProductsBetweenPrices(lowerPrice, upperPrice);

        if(!products.isEmpty()) {
            return products;
        } else {
            throw new NoProductsInDatabaseException("There are no products with these price range");
        }
    }

    private List<Product> findProductsBetweenPrices(BigDecimal lowerPrice, BigDecimal upperPrice) {
        return productRepository.findAll().orElseThrow(NoProductsInDatabaseException::new)
                .stream()
                .filter(p -> p.getPrice().compareTo(lowerPrice) >= 0 && p.getPrice().compareTo(upperPrice) <= 0)
                .collect(Collectors.toList());
    }

    private List<Product> findProductsByDescriptionAndHandleException() {
        try {
            return findProductsByDescription();
        } catch (NoProductsInDatabaseException e) {
            System.out.println(createStarFrame(e.getMessage()));
            return Collections.emptyList();
        }
    }

    private List<Product> findProductsByDescription() {
        System.out.print("Type product's description: ");
        String description = readLine();
        return productRepository.findByData(description, Product::getDescription)
                .orElseThrow(() -> new NoProductsInDatabaseException("There are no products with this description"));
    }
}
