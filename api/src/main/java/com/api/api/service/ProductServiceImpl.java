package com.api.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Category;
import com.api.api.entity.Product;
import com.api.api.repository.CategoryRepository;
import com.api.api.repository.ProductRepository;
import com.api.api.service.DAO.ProductDao;
import com.api.api.service.Interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(String productName, String productDescription, float price, int stock, MultipartFile image, Long categoryId) {
        // Verify and set the category
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create a new Product object
        Product product = new Product();
        product.setProductName(productName);
        product.setDescriptionProducto(productDescription);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        // Handle the image: Convert the MultipartFile to byte[]
        if (image != null && !image.isEmpty()) {
            try {
                product.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing the image", e);
            }
        }

        // Save and return the product
        return productRepository.save(product);
    }

    @Override
    public Product softDeleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setState(false);
        return productRepository.save(product);
    }

    @Override
    public Product updateProductStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStock(quantity);
        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productDao.getProductsByCategory(categoryId);
    }

    @Override
    public List<ProductDto> findByPriceRange(float minPrice, float maxPrice) {
        return productDao.findByPriceRange(minPrice, maxPrice);
    }
}
