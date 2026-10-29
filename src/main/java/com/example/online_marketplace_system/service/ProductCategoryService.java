package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.ProductCategory;
import com.example.online_marketplace_system.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    public String save(ProductCategory category) {
        repository.save(category);
        return "Product category saved successfully";
    }

    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    public Optional<ProductCategory> getById(UUID id) {
        return repository.findById(id);
    }

    public String update(UUID id, ProductCategory updated) {
        Optional<ProductCategory> existing = repository.findById(id);
        if (existing.isPresent()) {
            ProductCategory category = existing.get();
            category.setName(updated.getName());
            repository.save(category);
            return "Product category updated successfully";
        }
        return "The product category with that id does not exist";
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Product category deleted successfully";
        }
        return "The product category with that id does not exist";
    }
}