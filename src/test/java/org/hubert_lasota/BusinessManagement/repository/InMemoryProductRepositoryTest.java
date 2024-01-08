package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryProductRepositoryTest {
    InMemoryProductRepository inMemoryProductRepository = InMemoryProductRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryProductRepository.class.getDeclaredField("products");
        field.setAccessible(true);
        Map<Long, Product> products = (Map<Long, Product>) field.get(inMemoryProductRepository);
        products.clear();
    }


    @Test
    void shouldCountZero() {
        Long actual = inMemoryProductRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        int counter = 100;
        Product product;
        for(int i = 0; i < counter; i++) {
            product = new Product("product", BigDecimal.ONE);
            inMemoryProductRepository.save(product);
        }
        long repositoryCounter = inMemoryProductRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveProduct() {
        Product actual = new Product("product", BigDecimal.ONE);
        long repositorySizeBeforeSave = inMemoryProductRepository.count();

        Product expected = inMemoryProductRepository.save(actual);
        long repositorySizeAfterSave = inMemoryProductRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleProduct() {
        int counter = 10;
        String name;

        for(int i = 0; i < counter; i++) {
            name = "product test" + i;
            inMemoryProductRepository.save(new Product(name, BigDecimal.ZERO));
        }

        long repositorySizeAfterSave = inMemoryProductRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product("product", BigDecimal.ONE);
        inMemoryProductRepository.save(product);
        long repositorySizeAfterSave = inMemoryProductRepository.count();

        inMemoryProductRepository.delete(product.getId());
        long repositorySizeAfterDelete = inMemoryProductRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleProducts() {
        int counter = 10;
        Product[] products = new Product[counter];
        String name;

        for(int i = 0; i < counter; i++) {
            name = "product test " + i;
            products[i] = inMemoryProductRepository.save(new Product(name, BigDecimal.ONE));
        }
        long repositorySizeAfterSave = inMemoryProductRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryProductRepository.delete(products[i].getId());
        }
        long repositorySizeAfterDelete = inMemoryProductRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Product product = new Product("product", BigDecimal.ONE);
        inMemoryProductRepository.save(product);

        Product foundProduct = inMemoryProductRepository.findById(product.getId()).get();

        assertEquals(product, foundProduct);
    }

    @Test
    void shouldNotFindById() {
        Optional<Product> foundProduct = inMemoryProductRepository.findById(-1L);
        assertEquals(Optional.empty(), foundProduct);
    }

    @Test
    void shouldFindAll() {
        Product product1 = new Product("product", BigDecimal.ONE);
        Product product2 = new Product("product", BigDecimal.ONE);
        inMemoryProductRepository.save(product1);
        inMemoryProductRepository.save(product2);
        long repositorySizeAfterSave = inMemoryProductRepository.count();

        List<Product> foundProducts = inMemoryProductRepository.findAll().get();
        long foundProductsSize = foundProducts.size();

        assertEquals(repositorySizeAfterSave, foundProductsSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Product>> foundProducts = inMemoryProductRepository.findAll();
        assertEquals(Optional.empty(), foundProducts);
    }

    @Test
    void shouldFindByName() {
        Product pencil1 = new Product("pencilX1", BigDecimal.ONE);
        Product pencil2 = new Product("pencilX2", BigDecimal.ONE);
        Product pencil3 = new Product("pencilX3", BigDecimal.ONE);
        inMemoryProductRepository.save(pencil1);
        inMemoryProductRepository.save(pencil2);
        inMemoryProductRepository.save(pencil3);

        List<Product> pencils = List.of(pencil1, pencil2, pencil3);
        List<Product> foundProducts = inMemoryProductRepository.findByName("pencil").get();

        assertEquals(pencils, foundProducts);
    }

    @Test
    void shouldNotFindByName() {
        Optional<List<Product>> foundProduct = inMemoryProductRepository.findByName("emptyName");
        assertEquals(Optional.empty(), foundProduct);
    }

    @Test
    void shouldFindByDescription() {
        Product pencil1 = new Product("pencilX1", BigDecimal.ONE, "blue pencil");
        Product pencil2 = new Product("pencilX2", BigDecimal.ONE, "blue pencil");
        Product pencil3 = new Product("pencilX3", BigDecimal.ONE, "blue pencil");
        inMemoryProductRepository.save(pencil1);
        inMemoryProductRepository.save(pencil2);
        inMemoryProductRepository.save(pencil3);

        List<Product> pencils = List.of(pencil1, pencil2, pencil3);
        List<Product> foundProducts = inMemoryProductRepository.findByDescription("blue pencil").get();

        assertEquals(pencils, foundProducts);
    }

    @Test
    void shouldNotFindByDescription() {
        Optional<List<Product>> foundProduct = inMemoryProductRepository.findByDescription("emptyDescription");
        assertEquals(Optional.empty(), foundProduct);
    }

    @Test
    void shouldFindBetweenPrices() {
        Product pencil1 = new Product("pencilX1", new BigDecimal("1.00"), "blue pencil");
        Product pencil2 = new Product("pencilX2", new BigDecimal("2.50"), "blue pencil");
        Product pencil3 = new Product("pencilX3", new BigDecimal("3.00"), "blue pencil");
        inMemoryProductRepository.save(pencil1);
        inMemoryProductRepository.save(pencil2);
        inMemoryProductRepository.save(pencil3);

        List<Product> pencils = List.of(pencil1, pencil2, pencil3);
        List<Product> foundProducts = inMemoryProductRepository.findBetweenPrices(BigDecimal.ONE, new BigDecimal("3.00")).get();

        assertEquals(pencils, foundProducts);
    }

    @Test
    void shouldNotFindBetweenPrices() {
        Optional<List<Product>> foundProduct = inMemoryProductRepository.findBetweenPrices(BigDecimal.ZERO, BigDecimal.ONE);
        assertEquals(Optional.empty(), foundProduct);
    }

}
