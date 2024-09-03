package com.api.api.service.Interfaces;

import java.util.Optional;

import com.api.api.DTO.CartDto;
import com.api.api.entity.Product;

public interface CartService {

    void createCart(Long idUsuario);

    String addProduct(Long idProducto, Long idUsuario, int cantidad);

    Optional<CartDto> findCart(Long idUsuario);

    String removeProduct(Long idProducto, Long idUsuario);

    String decreaseCart(Long idUsuario, Long idProducto, int cantidad);

    void placeOrder(Long idUsuario);

    void updatePricesInCarts(Product product);
}
