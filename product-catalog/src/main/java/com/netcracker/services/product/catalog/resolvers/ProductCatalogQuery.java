package com.netcracker.services.product.catalog.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.netcracker.services.product.catalog.model.Category;
import com.netcracker.services.product.catalog.repository.CategoryRepository;
import com.netcracker.services.product.catalog.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductCatalogQuery implements GraphQLQueryResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCatalogQuery.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<Category> getCategories() {
        LOGGER.info("Category find");
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        LOGGER.info("Category find: id={}", id);
        return categoryRepository.findById(id);
    }


}
