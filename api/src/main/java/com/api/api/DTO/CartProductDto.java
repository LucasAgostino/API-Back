package com.api.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {
    private Long cartProductId;
    private String productName;
    private int quantity;
    private float totalPrice;
}
