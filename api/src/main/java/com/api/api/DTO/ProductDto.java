package com.api.api.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private float price;
    private List<String> imageBase64s;
    private String productDescription;
    private int stock;
    private String categoryName;
    private boolean active;
}
