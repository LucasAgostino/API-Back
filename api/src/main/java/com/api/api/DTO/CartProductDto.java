package com.api.api.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {
    private Long cartProductId;
    private Long productId;
    private String productName;
    private int quantity;
    private float totalPrice;
    private float discountPrice;
    private List<String> imageBase64s;
    private String categoryName;
}
