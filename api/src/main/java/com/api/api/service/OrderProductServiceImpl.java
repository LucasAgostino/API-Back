package com.api.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.entity.Order;
import com.api.api.entity.OrderProduct;
import com.api.api.entity.Product;
import com.api.api.repository.OrderProductRepository;
import com.api.api.repository.OrderRepository;
import com.api.api.repository.ProductRepository;
import com.api.api.service.Interfaces.OrderProductService;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(Long orderId, Long productId, int quantity) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        Order order = orderOpt.orElseThrow(() -> new RuntimeException("Order not found"));
        Optional<Product> productOpt = productRepository.findById(productId);
        Product product = productOpt.orElseThrow(() -> new RuntimeException("Product not found"));
        
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(quantity);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setTotalPrice(product.getPrice() * quantity);
        orderProductRepository.save(orderProduct);
    }
    
}
