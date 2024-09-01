package com.api.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList; // Add this import statement

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Category;
import com.api.api.entity.Product;
import com.api.api.entity.ProductImage;
import com.api.api.repository.CategoryRepository;
import com.api.api.repository.ProductImagesRepository;
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

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Override
    public Product createProduct(String productName, String productDescription, float price, int stock, List<MultipartFile> images, Long categoryId) {
        // Verificar y establecer la categoría
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    
        // Crear un nuevo objeto Product
        Product product = new Product();
        product.setProductName(productName);
        product.setDescriptionProducto(productDescription);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
    
        // Guardar el producto primero
        Product savedProduct = productRepository.save(product);
        System.out.println("Product saved with ID: " + savedProduct.getProductId());
    
        // Manejar las imágenes: Usar la función para agregar imágenes al producto ya guardado
        if (images != null && !images.isEmpty()) {
            addImagesToProduct(savedProduct.getProductId(), images);
        }
    
        // Devolver el producto con las imágenes
        return savedProduct;
    }
    
    

    
    @Override
    public Product addImagesToProduct(Long productId, List<MultipartFile> images) {
        // Buscar el producto por ID
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    
        // Procesar las imágenes
        if (images != null && !images.isEmpty()) {
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile image : images) {
                if (image != null && !image.isEmpty()) {
                    try {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageData(image.getBytes());
                        productImage.setProduct(product); 
                        productImages.add(productImage);
                        productImagesRepository.save(productImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Error processing the image", e);
                    }
                } else {
                    System.out.println("Empty or null image file skipped.");
                }
            }
            product.getImages().addAll(productImages); // Agregar nuevas imágenes al producto existente
        } else {
            System.out.println("No images provided.");
        }
    
        // Guardar y devolver el producto actualizado
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
