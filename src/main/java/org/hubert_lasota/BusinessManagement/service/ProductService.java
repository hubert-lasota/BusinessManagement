package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.product.Product;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

    Product saveProduct(Product product);

    Product findProductById(Long id);

    List<Product> findProductsByName(String name);

    List<Product> findProductsBetweenPrices(BigDecimal lowerPrice, BigDecimal upperPrice);

    List<Product> findProductsByDescription(String description);

    List<Product> findAllProducts();

    void updateProductsName(Long id, String name);

    void updateProductsPrice(Long id, BigDecimal price);

    void updateProductsDescription(Long id, String description);

    void deleteProductById(Long id);

}
