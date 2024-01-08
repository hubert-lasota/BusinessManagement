package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.product.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long> {

    Optional<List<Product>> findByName(String name);

    Optional<List<Product>> findBetweenPrices(BigDecimal lowerPrice, BigDecimal upperPrice);

    Optional<List<Product>> findByDescription(String description);

}
