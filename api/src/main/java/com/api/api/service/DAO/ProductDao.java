package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.repository.ProductRepository;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Base64;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Product;

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
    dto.setDiscountPercentage(product.getDiscountPercentage());
    List<String> imageBase64s = product.getImages().stream()
        .map(productImage -> {
            try {
                Blob imageBlob = productImage.getImageData();
                byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (SQLException e) {
                throw new RuntimeException("Error al convertir Blob a Base64", e);
            }
        })
        .collect(Collectors.toList());
    dto.setImageBase64s(imageBase64s);
    dto.setCategoryName(product.getCategory().getCategoryName());
    dto.setStock(product.getStock());
    dto.setActive(product.getState());
    dto.setProductDescription(product.getDescriptionProducto());
    dto.setTags(product.getTags());
    return dto;
}

    

}
