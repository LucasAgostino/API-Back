package com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;

import com.api.api.DTO.OrderDto;
import com.api.api.service.Interfaces.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/get")
    public List<OrderDto> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<OrderDto> getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/admin/get/byuser/{userId}")
    public List<OrderDto> getOrdersByUser(@PathVariable Long userId) {
        return orderService.findByUserId(userId);
    }
}
