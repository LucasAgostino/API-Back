package com.api.api.repository;

import org.springframework.stereotype.Repository;

import com.api.api.entity.Product;
import com.api.api.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')) AND p.state = true")
    List<Product> findByProductNameContainingIgnoreCase(@Param("name") String productName);


   @Query("SELECT p FROM Product p WHERE p.stock > 0 AND p.state = true")
   List<Product> findAllActiveProducts();

   Optional<Product> findByProductName(String productName);

   @Query("SELECT p FROM Product p " +
       "LEFT JOIN p.tags t " +
       "WHERE (:minPrice IS NULL OR p.price >= :minPrice) " +
       "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
       "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
       "AND p.state = true " +
       "AND (:tags IS NULL OR t IN :tags)"+
       "AND p.stock > 0")
List<Product> findProductsByPriceCategoryAndTags(@Param("minPrice") Float minPrice,
                                                  @Param("maxPrice") Float maxPrice,
                                                  @Param("categoryId") Long categoryId,
                                                  @Param("tags") Set<Tag> tags);






}
