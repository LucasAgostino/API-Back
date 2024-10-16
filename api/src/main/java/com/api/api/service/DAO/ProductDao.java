package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.repository.ProductRepository;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Base64;
import com.api.api.entity.Tag;

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
    public List<ProductDto> filterProducts(Float minPrice, Float maxPrice, Long categoryId, Set<Tag> tags) {
        List<Product> products = productRepository.findProductsByPriceCategoryAndTags(minPrice, maxPrice, categoryId, tags);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private ProductDto convertToDTO(Product product) {
    ProductDto dto = new ProductDto();
    dto.setProductId(product.getProductId());
    dto.setProductName(product.getProductName());
    dto.setPrice(product.getPrice());
    dto.setDiscountPercentage(product.getDiscountPercentage());
    
    // Lista para almacenar los base64 de las imágenes
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
    
    // Lista para almacenar los imageId correspondientes
    List<Long> imageIds = product.getImages().stream()
        .map(ProductImage::getImageId) // Suponiendo que ProductImage tiene un método getImageId()
        .collect(Collectors.toList());
    
    // Asignamos las listas paralelas
    dto.setImageBase64s(imageBase64s);  // Seteamos los base64
    dto.setImageid(imageIds);          // Seteamos los IDs de las imágenes
    
    dto.setCategoryName(product.getCategory().getCategoryName());
    dto.setStock(product.getStock());
    dto.setActive(product.getState());
    dto.setProductDescription(product.getDescriptionProducto());
    dto.setTags(product.getTags());
    
    return dto;
}

}
