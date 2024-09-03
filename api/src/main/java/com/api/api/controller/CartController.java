package com.api.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.DTO.CartDto;
import com.api.api.service.Interfaces.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(
            @RequestParam Long productId,
            @RequestParam Long userId,
            @RequestParam int quantity) {
        String message = cartService.addProduct(productId, userId, quantity);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/updateproduct")
    public ResponseEntity<String> updatePrdouct(
            @RequestParam Long productId,
            @RequestParam Long userId,
            @RequestParam int quantity) {
        String message = cartService.addProduct(productId, userId, quantity);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get")
    public Optional<CartDto> getCart(@RequestParam Long userId) {
        return cartService.findCart(userId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProduct(
            @RequestParam Long productId,
            @RequestParam Long userId) {
        String message = cartService.removeProduct(productId, userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/decrease")
    public ResponseEntity<String> decreaseCart(
            @RequestParam Long productId,
            @RequestParam Long userId,
            @RequestParam int quantity) {
        String message = cartService.decreaseCart(userId, productId, quantity);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/checkout")
    public void checkoutCart(@RequestParam Long userId) {
        cartService.placeOrder(userId);
    }
}
