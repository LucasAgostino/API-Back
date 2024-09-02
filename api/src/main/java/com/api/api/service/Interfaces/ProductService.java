package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;

public interface ProductService {

    public ProductDto createProduct(String productName, String productDescription, float price, int stock, List<MultipartFile> images, Long categoryId);

    public ProductDto addImagesToProduct(Long productId, List<MultipartFile> images);
    
    public ProductDto softDeleteProduct(Long productId);

    public ProductDto updateProductStock(Long productId, int quantity);

    public List<ProductDto> getAllProducts();

    public Optional<ProductDto> findById(Long productId);

    public List<ProductDto> getProductsByCategory(Long categoryId);
    
    public List<ProductDto> findByPriceRange(float minPrice, float maxPrice);
}
