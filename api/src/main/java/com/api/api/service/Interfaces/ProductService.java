package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.api.api.entity.Tag;
import com.api.api.DTO.ProductDto;

public interface ProductService {

    public ProductDto createProduct(String productName, String productDescription, float price, float discountPercentage, int stock, List<MultipartFile> images, Long categoryId, Set<Tag> tags);

    public ProductDto addImagesToProduct(Long productId, List<MultipartFile> images);
    
    public ProductDto softDeleteProduct(Long productId);

    public ProductDto updateProductStock(Long productId, int quantity);

    public List<ProductDto> getAllProducts();

    public Optional<ProductDto> findById(Long productId);

    public List<ProductDto> getProductsByCategory(Long categoryId);
    
    public List<ProductDto> findByPriceRange(float minPrice, float maxPrice);

    public ProductDto updateProductDiscount(Long productId, float discountPercentage);

    public ProductDto addTagToProduct(Long productId, Tag tag);

    public ProductDto removeTagFromProduct(Long productId, Tag tag);
}
