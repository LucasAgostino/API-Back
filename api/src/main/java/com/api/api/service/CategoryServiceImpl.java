package com.api.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.entity.Category;
import com.api.api.exception.CategoryExists;
import com.api.api.repository.CategoryRepository;
import com.api.api.service.Interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
    if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
        throw new CategoryExists("La categoría ya existe");
    }
    return categoryRepository.save(category);
    }


    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
