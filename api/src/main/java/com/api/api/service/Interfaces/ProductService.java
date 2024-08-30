package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Product;

public interface ProductService {

    public Product createProduct(String productName, String productDescription, float price, int stock, MultipartFile image, Long categoryId);

    public Product softDeleteProduct(Long productId);

    public Product updateProductStock(Long productId, int quantity);

    public List<ProductDto> getAllProducts();

    public Optional<Product> findById(Long productId);

    public List<ProductDto> getProductsByCategory(Long categoryId);
    
    public List<ProductDto> findByPriceRange(float minPrice, float maxPrice);
}
