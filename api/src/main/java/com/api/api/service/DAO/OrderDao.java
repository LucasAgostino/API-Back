package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.api.api.DTO.OrderDto;
import com.api.api.DTO.OrderProductDto;
import com.api.api.entity.Order;
import com.api.api.entity.OrderProduct;
import com.api.api.repository.OrderProductRepository;
import com.api.api.repository.OrderRepository;

@Service
public class OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrderDto> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserName(order.getUser().getEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalOrder(order.getOrderTotal());

        // Fetch and set OrderProduct details
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder_OrderId(order.getOrderId());
        Set<OrderProductDto> orderProductDtos = orderProducts.stream()
                .map(this::convertToOrderProductDto)
                .collect(Collectors.toSet());
        dto.setOrderProducts(orderProductDtos);

        return dto;
    }

    private OrderProductDto convertToOrderProductDto(OrderProduct orderProduct) {
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setOrderId(orderProduct.getOrder().getOrderId());
        orderProductDto.setProductName(orderProduct.getProduct().getProductName());
        orderProductDto.setQuantity(orderProduct.getQuantity());
        orderProductDto.setTotalPrice(orderProduct.getTotalPrice());
        orderProductDto.setPrice(orderProduct.getPrice());
        return orderProductDto;
    }
}
