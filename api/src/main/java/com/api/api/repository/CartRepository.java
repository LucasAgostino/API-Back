package com.api.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Method to find a cart by user ID
    Optional<Cart> findByUserId(Long userId);
}
