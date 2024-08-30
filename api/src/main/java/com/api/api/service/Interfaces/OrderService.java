package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.api.DTO.OrderDto;
import com.api.api.entity.Order;

public interface OrderService {
    List<OrderDto> findAll();
    Optional<OrderDto> findById(Long id);
    List<OrderDto> findByUserId(Long userId);
    Order createOrder(Long userId, float total);
}
