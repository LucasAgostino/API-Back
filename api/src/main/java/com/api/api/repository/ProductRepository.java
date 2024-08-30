package com.api.api.repository;

import org.springframework.stereotype.Repository;

import com.api.api.entity.Product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.stock > 0 AND p.state = true")
    List<Product> findAllActiveProducts();

       @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
       List<Product> findAllProductsByCategory(@Param("categoryId") Long categoryId);
   

    @Query("SELECT p FROM Product p WHERE " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> findProductsByPriceRange(@Param("minPrice") float minPrice, 
                                           @Param("maxPrice") float maxPrice);
}
