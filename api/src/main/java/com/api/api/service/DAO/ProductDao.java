package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Product;
import com.api.api.entity.ProductImage;

@Service
public class ProductDao {
    
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAllActiveProducts();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());    
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return convertToDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findByPriceRange(Float minPrice, Float maxPrice) {
        List<Product> products = productRepository.findProductsByPriceRange(minPrice, maxPrice);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findAllProductsByCategory(categoryId);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private ProductDto convertToDTO(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        List<byte[]> imageBytes = product.getImages().stream()
                                        .map(ProductImage::getImageData)
                                        .collect(Collectors.toList());
        dto.setImages(imageBytes);
        return dto;
    }
}
