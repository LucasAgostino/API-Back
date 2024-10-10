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

    public List<ProductDto> getAllProducts();

    public Optional<ProductDto> findById(Long productId);

    public ProductDto updateProduct(Long productId, Integer stock, Float discountPercentage, Float price, String name, Tag tag, String description);

    public ProductDto removeImageFromProduct(Long productId, Long ImageId);
    
    public ProductDto removeTagFromProduct(Long productId, Tag tag);

    public Set<Tag> getAllTags();

    public List<ProductDto> filterProducts(Float minPrice, Float maxPrice, Long categoryId, Set<Tag> tags);
}
