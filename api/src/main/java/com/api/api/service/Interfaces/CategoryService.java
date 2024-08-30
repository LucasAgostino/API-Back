package com.api.api.service.Interfaces;

import java.util.List;

import com.api.api.entity.Category;

public interface CategoryService {

    public List<Category> getAll();
    
    public Category createCategory(Category categoria);

    public Category getCategoryById(Long idCategoria);
}
