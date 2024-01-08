package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {
    private static InMemoryProductRepository inMemoryProductRepository;
    private final Map<Long, Product> products;

    private InMemoryProductRepository() {
        products = new HashMap<>();
    }

    public static InMemoryProductRepository getInstance() {
        if(inMemoryProductRepository == null) {
            inMemoryProductRepository = new InMemoryProductRepository();
        }
        return inMemoryProductRepository;
    }

    @Override
    public Product save(Product product) {
        products.putIfAbsent(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<List<Product>> findByName(String name) {
        return findByData(name, Product::getName);
    }

    @Override
    public Optional<List<Product>> findBetweenPrices(BigDecimal lowerPrice, BigDecimal upperPrice) {
        return findProductsBetweenComparableData(lowerPrice, upperPrice, Product::getPrice);
    }

    private <T extends Comparable<T>> Optional<List<Product>> findProductsBetweenComparableData(
            T start, T end, Function<Product, T> fieldExtractor) {
        List<Product> productsBetweenData = products.values()
                .stream()
                .filter(p -> fieldExtractor.apply(p).compareTo(start) >= 0)
                .filter(p -> fieldExtractor.apply(p).compareTo(end) <= 0)
                .collect(Collectors.toList());

        return productsBetweenData.isEmpty() ? Optional.empty() : Optional.of(productsBetweenData);
    }

    @Override
    public Optional<List<Product>> findByDescription(String description) {
        return findByData(description, Product::getDescription);
    }

    private Optional<List<Product>> findByData(String data, Function<Product, String> fieldExtractor) {
        List<Product> foundProducts = products.values().stream()
                .filter(p -> fieldExtractor.apply(p).contains(data))
                .collect(Collectors.toList());

        return foundProducts.isEmpty() ? Optional.empty() : Optional.of(foundProducts);
    }

    @Override
    public Optional<List<Product>> findAll() {
        List<Product> foundProducts = List.copyOf(products.values());
        return foundProducts.isEmpty() ? Optional.empty() : Optional.of(foundProducts);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id).orElseThrow(NoSuchIdException::new);
        products.remove(id, product);
    }

    @Override
    public Long count() {
        return (long) products.size();
    }

}
