package com.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.entity.Cart;
import com.api.api.entity.CartProduct;
import com.api.api.entity.Product;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    // Method to find all products in a specific cart
    List<CartProduct> findByCart(Cart cart);

    CartProduct findByCartAndProduct(Cart cart, Product product);
}
