package com.api.api.service.Interfaces;

public interface OrderProductService {
    void addProduct(Long orderId, Long productId, int quantity);
}
