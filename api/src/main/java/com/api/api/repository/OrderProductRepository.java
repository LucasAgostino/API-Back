package com.api.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByOrder_OrderId(Long orderId);  
}
