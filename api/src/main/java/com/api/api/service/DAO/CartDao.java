package com.api.api.service.DAO;

import com.api.api.DTO.CartDto;
import com.api.api.DTO.CartProductDto;
import com.api.api.entity.Cart;
import com.api.api.entity.CartProduct;
import com.api.api.repository.CartProductRepository;
import com.api.api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartDao {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Transactional(readOnly = true)
    public Optional<CartDto> findByCartId(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        return cartOpt.map(this::convertToCartDto);
    }

    private CartProductDto convertToCartProductDto(CartProduct cartProduct) {
        CartProductDto dto = new CartProductDto();
        dto.setCartProductId(cartProduct.getCartProductId());
        dto.setProductName(cartProduct.getProduct().getProductName());
        dto.setQuantity(cartProduct.getQuantity());
        dto.setTotalPrice(cartProduct.getTotalPrice());
        return dto;
    }

    private CartDto convertToCartDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setCartId(cart.getCartId());
    
        List<CartProduct> cartProducts = cartProductRepository.findByCart(cart);
    
        List<CartProductDto> productsDto = cartProducts.stream()
                .map(this::convertToCartProductDto)
                .collect(Collectors.toList());
    
        dto.setProducts(productsDto);
        return dto;
    }
}
