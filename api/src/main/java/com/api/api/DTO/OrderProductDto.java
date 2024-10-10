package com.api.api.DTO;

import lombok.Data;

@Data
public class OrderProductDto {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private float totalPrice;
    private float price;
}
