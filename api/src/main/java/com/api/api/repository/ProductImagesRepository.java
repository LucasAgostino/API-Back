package com.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.api.entity.ProductImage;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage, Long> {

    
}
