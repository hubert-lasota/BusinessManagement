package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.product.Product;
import org.hubert_lasota.BusinessManagement.exception.NoProductsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product saveProduct(Product product) {
        if(isProductPresentInDatabase(product.getId())) {
            return product;
        }
        return productRepository.save(product);
    }

    private boolean isProductPresentInDatabase(Long id) {
        return productRepository.findById(id).isPresent();
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new NoProductsInDatabaseException("There are no products with this name"));
    }

    @Override
    public List<Product> findProductsBetweenPrices(BigDecimal lowerPrice, BigDecimal upperPrice) {
        return productRepository.findBetweenPrices(lowerPrice, upperPrice)
                .orElseThrow(() -> new NoProductsInDatabaseException("There are no products between these prices"));
    }

    @Override
    public List<Product> findProductsByDescription(String description) {
        return productRepository.findByDescription(description)
                .orElseThrow(() -> new NoProductsInDatabaseException("There are no products with this description"));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll().orElseThrow(NoProductsInDatabaseException::new);
    }

    @Override
    public void updateProductsName(Long id, String name) {
        Product productToUpdate = findProductById(id);
        productToUpdate.setName(name);
    }

    @Override
    public void updateProductsPrice(Long id, BigDecimal price) {
        Product productToUpdate = findProductById(id);
        productToUpdate.setPrice(price);
    }

    @Override
    public void updateProductsDescription(Long id, String description) {
        Product productToUpdate = findProductById(id);
        productToUpdate.setDescription(description);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.delete(id);
    }

}
