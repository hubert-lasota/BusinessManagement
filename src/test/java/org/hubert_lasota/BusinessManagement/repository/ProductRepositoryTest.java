package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductRepositoryTest {
    ProductRepository productRepository = ProductRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = ProductRepository.class.getDeclaredField("products");
        field.setAccessible(true);
        Map<Long, Product> products = (Map<Long, Product>) field.get(productRepository);
        products.clear();
    }


    @Test
    void shouldCountZero() {
        Long actual = productRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Product product;
        for(int i = 0; i < counter; i++) {
            product = new Product("product", BigDecimal.ONE);
            productRepository.save(product);
        }
        long repositoryCounter = productRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveProduct() {
        Product actual = new Product("product", BigDecimal.ONE);
        long repositorySizeBeforeSave = productRepository.count();

        Product expected = productRepository.save(actual);
        long repositorySizeAfterSave = productRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleProduct() {
        int counter = 10;
        String name;

        for(int i = 0; i < counter; i++) {
            name = "product test" + i;
            productRepository.save(new Product(name, BigDecimal.ZERO));
        }

        long repositorySizeAfterSave = productRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product("product", BigDecimal.ONE);;
        productRepository.save(product);
        long repositorySizeAfterSave = productRepository.count();

        productRepository.delete(product.getID());
        long repositorySizeAfterDelete = productRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleProducts() {
        int counter = 10;
        Product[] products = new Product[counter];
        String name;

        for(int i = 0; i < counter; i++) {
            name = "product test " + i;
            products[i] = productRepository.save(new Product("product", BigDecimal.ONE));
        }
        long repositorySizeAfterSave = productRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            productRepository.delete(products[i].getID());
        }
        long repositorySizeAfterDelete = productRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Product product = new Product("product", BigDecimal.ONE);
        productRepository.save(product);

        Product foundProduct = productRepository.findById(product.getID()).get();

        assertEquals(product, foundProduct);
    }

    @Test
    void shouldNotFindById() {
        Optional<Product> foundProduct = productRepository.findById(-1L);
        assertEquals(Optional.empty(), foundProduct);
    }

    @Test
    void shouldFindByData() {
        Product product1 = new Product("product", BigDecimal.ONE);
        Product product2 = new Product("product", BigDecimal.ONE);
        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> foundProducts = productRepository.findByData("product", Product::getName).get();

        assertEquals(product1, foundProducts.get(0));
        assertEquals(product2, foundProducts.get(1));
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<Product>> foundProducts = productRepository.findByData("This data doesn't exists", Product::getName);
        assertEquals(Optional.empty(), foundProducts);
    }

    @Test
    void shouldFindAll() {
        Product product1 = new Product("product", BigDecimal.ONE);
        Product product2 = new Product("product", BigDecimal.ONE);
        productRepository.save(product1);
        productRepository.save(product2);
        long repositorySizeAfterSave = productRepository.count();

        List<Product> foundProducts = productRepository.findAll().get();
        long foundProductsSize = foundProducts.size();

        assertEquals(repositorySizeAfterSave, foundProductsSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Product>> foundProducts = productRepository.findAll();
        assertEquals(Optional.empty(), foundProducts);
    }

}
