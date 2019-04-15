package com.netcracker.services.product.catalog.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.netcracker.services.product.catalog.model.Category;
import com.netcracker.services.product.catalog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogMutations implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCatalogMutations.class);

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        LOGGER.info("Category add: category={}", category);
        return categoryRepository.save(category);
    }

    public boolean deleteCategory(Long id) {
        LOGGER.info("Category delete: id={}", id);

        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Category updateCategory(Long id, Category category) {
        LOGGER.info("Category update: id={}, category={}", id, category);
        Category updatedCategory = null;
        if (categoryRepository.findById(id).isPresent()) {
            category.setId(id);
            updatedCategory = categoryRepository.save(category);
        }
        return updatedCategory;
    }

}
