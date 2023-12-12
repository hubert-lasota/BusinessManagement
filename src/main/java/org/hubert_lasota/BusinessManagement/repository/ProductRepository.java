package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.product.Product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductRepository implements Repository<Product, Long> {
    private static ProductRepository productRepository;
    private final Map<Long, Product> products;

    private ProductRepository() {
        products = new HashMap<>();
    }

    public static ProductRepository getInstance() {
        if(productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    @Override
    public Product save(Product product) {
        products.putIfAbsent(product.getID(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<List<Product>> findByData(String data, Function<Product, String> fieldExtractor) {
        List<Product> tempList = products.values().stream()
                .filter(p -> fieldExtractor.apply(p).contains(data))
                .collect(Collectors.toList());

        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<Product>> findAll() {
        List<Product> tempList = List.copyOf(products.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id).orElseThrow(NoSuchIdException::new);
        products.remove(product.getID(), product);
    }

    @Override
    public Long count() {
        return (long) products.size();
    }
}
