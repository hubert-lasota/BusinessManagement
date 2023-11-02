package org.hubert_lasota.BusinessManagement.product;


import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductRepository {
    private static ProductRepository productRepository;
    private Map<Long, Product> products;

    private ProductRepository() {
        products = new HashMap<>();
    }

    public static ProductRepository getInstance() {
        if(productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public Product save(Product product) {
        return products.putIfAbsent(product.getID(), product);
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Optional<List<Product>> findByData(String data, Function<Product, String> fieldExtractor) {
        List<Product> tempList = products.values().stream()
                .filter(p -> fieldExtractor.apply(p).contains(data))
                .collect(Collectors.toList());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    public Optional<List<Product>> findAll() {
        List<Product> tempList = List.copyOf(products.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    public Product update(Long id, Product product) {
        Product tempProduct = findById(id).orElseThrow(NoSuchIdException::new);
        return update(tempProduct, product);
    }

    private Product update(Product productToUpdate, Product productUpdater) {
        productToUpdate.setName(productUpdater.getName());
        productToUpdate.setPrice(productUpdater.getPrice());
        productToUpdate.setDescription(productUpdater.getDescription());
        return productToUpdate;
    }

    public void delete(Long id) {
        Product product = findById(id).orElseThrow(NoSuchIdException::new);
        products.remove(product.getID(), product);
    }
}
