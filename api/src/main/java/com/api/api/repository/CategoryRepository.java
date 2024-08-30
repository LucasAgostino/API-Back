package com.api.api.repository;

import org.springframework.stereotype.Repository;

import com.api.api.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    
}