
package com.netcracker.services.product.catalog.controller;

import com.netcracker.services.product.catalog.exception.NotFoundException;
import com.netcracker.services.product.catalog.model.Category;
import com.netcracker.services.product.catalog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryRepository repository;

    @GetMapping("/categories")
    public List<Category> findAll() {
        LOGGER.info("Category find");
        return repository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category findById(@PathVariable("id") Long id) {
        LOGGER.info("Category find: id={}", id);
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new NotFoundException("Category not found with id " + id);
        }
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        LOGGER.info("createCategory : ", category);
        return repository.save(category);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable long id, @RequestBody Category categoryUpdated) {
        LOGGER.info("updateCategory : id={}", id + " " + categoryUpdated);
        return repository.findById(id)
                .map(category -> {
                    category.setCategoryName(categoryUpdated.getCategoryName());
                    return repository.save(category);
                }).orElseThrow(() -> new NotFoundException("Category not found with id " + id));
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable long id) {
        LOGGER.info("Category delete: id={}", id);
        return repository.findById(id)
                .map(category -> {
                    repository.delete(category);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Category not found with id " + id));
    }
}

