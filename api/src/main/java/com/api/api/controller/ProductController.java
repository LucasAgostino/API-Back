package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.service.Interfaces.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ProductDto createProduct(@RequestParam String productName,
                                    @RequestParam String productDescription,
                                    @RequestParam float price,
                                    @RequestParam int stock,
                                    @RequestParam(required = false) List<MultipartFile> images,
                                    @RequestParam Long categoryId) {
        return productService.createProduct(productName, productDescription, price, stock, images, categoryId);
    }

    @PostMapping("/add-images")
    public ProductDto addImagesToProduct(
        @RequestParam Long productId,
        @RequestParam List<MultipartFile> images) {
    return productService.addImagesToProduct(productId, images);
    }


    @PutMapping("/delete/{id}")
    public ProductDto softDeleteProduct(@PathVariable("id") Long productId) {
        return productService.softDeleteProduct(productId);
    }

    @PutMapping("/update/{id}")
    public ProductDto updateProductStock(@PathVariable("id") Long productId, @RequestParam int stock) {
        return productService.updateProductStock(productId, stock);
    }
    
    @GetMapping("/get")
    public List<ProductDto> getAll() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/get/{id}")
    public Optional<ProductDto> getProductById(@PathVariable("id") Long productId) {
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
}
