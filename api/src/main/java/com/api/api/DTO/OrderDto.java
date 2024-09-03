package com.api.api.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class OrderDto {
    private Long orderId;
    private String userName;
    private LocalDateTime orderDate;
    private float totalOrder;
    private Set<OrderProductDto> orderProducts;
}
