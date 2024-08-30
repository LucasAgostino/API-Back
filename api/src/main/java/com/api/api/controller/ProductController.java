package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Product;
import com.api.api.service.Interfaces.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Product createProduct(@RequestParam String productName,
                                 @RequestParam String productDescription,
                                 @RequestParam float price,
                                 @RequestParam int stock,
                                 @RequestParam MultipartFile image,
                                 @RequestParam Long categoryId) {
        return productService.createProduct(productName, productDescription, price, stock, image, categoryId);
    }

    @PostMapping("/delete/{id}")
    public Product softDeleteProduct(@PathVariable("id") Long productId) {
        return productService.softDeleteProduct(productId);
    }

    @PostMapping("/update/{id}")
    public Product updateProductStock(@PathVariable("id") Long productId, @RequestParam int stock) {
        return productService.updateProductStock(productId, stock);
    }
    
    @GetMapping("/get")
    public List<ProductDto> getAll() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/get/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long productId) {
        return productService.findById(productId);
    }

    @GetMapping("/get/cat/{category}")
    public List<ProductDto> getByCategory(@PathVariable("category") Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/get/filter")
    public List<ProductDto> filterProductsByPrice(@RequestParam(required = false) Float minPrice,
                                                  @RequestParam(required = false) Float maxPrice) {
        return productService.findByPriceRange(minPrice, maxPrice);
    }    

    @GetMapping("/get/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            byte[] image = product.getImage();

            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Adjust according to the image format
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Image not found
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Product not found
        }
    }
}
