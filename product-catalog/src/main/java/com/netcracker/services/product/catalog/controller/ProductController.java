
package com.netcracker.services.product.catalog.controller;

import com.netcracker.services.product.catalog.exception.NotFoundException;
import com.netcracker.services.product.catalog.model.Product;
import com.netcracker.services.product.catalog.repository.CategoryRepository;
import com.netcracker.services.product.catalog.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products")
    public List<Product> findAll() {
        LOGGER.info("Product find");
        return productRepository.findAll();
    }

    @GetMapping("/categories/{categoryId}/products")
    public List<Product> findById(@PathVariable("categoryId") Long id) {
        LOGGER.info("Product find: id={}", id);
        return productRepository.findByCategoryId(id);
    }

    @PostMapping("/categories/{categoryId}/products")
    public Product addProduct(@PathVariable("categoryId") Long categoryId, @RequestBody Product product) {
        LOGGER.info("Product add: " + product + "categoryId={}", categoryId);
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    product.setCategory(category);
                    return productRepository.save(product);
                }).orElseThrow(() -> new NotFoundException("Category not found!"));
    }


    @PutMapping("/categories/{categoryId}/products/{productId}")
    public Product updateProduct(@PathVariable("categoryId") Long categoryId, @PathVariable("productId") Long productId, @RequestBody Product productUpdated) {
        LOGGER.info("Product update: " + productUpdated + "categoryId={}", categoryId, "productId={}", productId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found!");
        }
        return productRepository.findById(productId)
                .map(product -> {
                    product.setProductName(productUpdated.getProductName());
                    product.setAverageUsage(productUpdated.getAverageUsage());
                    product.setOfferName(productUpdated.getOfferName());
                    return productRepository.save(product);
                }).orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    @DeleteMapping("/categories/{categoryId}/products/{productId}")
    public String deleteProduct(@PathVariable("categoryId") Long categoryId, @PathVariable("productId") Long productId) {
        LOGGER.info("Product delete: categoryId={}", categoryId, "productId={}", productId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found!");
        }

        return productRepository.findById(productId)
                .map(product -> {
                    productRepository.delete(product);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Produuct not found!"));
    }
}
