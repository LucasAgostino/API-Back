package com.api.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Tag;
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
    public ProductDto createProduct(
            @RequestParam String productName,
            @RequestParam String productDescription,
            @RequestParam float price,
            @RequestParam(required = false) float discountPercentage,
            @RequestParam int stock,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam Long categoryId,
            @RequestParam List<String> tags) {
                Set<Tag> tagSet = tags.stream()
                                    .map(Tag::valueOf) 
                                    .collect(Collectors.toSet());

    return productService.createProduct(productName, productDescription, price, discountPercentage, stock, images, categoryId, tagSet);
    }

    @PutMapping("/add-images")
    public ProductDto addImagesToProduct(
        @RequestParam Long productId,
        @RequestParam List<MultipartFile> images) {
    return productService.addImagesToProduct(productId, images);
    }


    @PutMapping("/delete/{id}")
    public ProductDto softDeleteProduct(@PathVariable("id") Long productId) {
        return productService.softDeleteProduct(productId);
    }
    
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
        @PathVariable Long productId,
        @RequestParam(required = false) Integer stock,
        @RequestParam(required = false) Float discountPercentage,
        @RequestParam(required = false) Float price,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) List<String> tags, // Cambiado a List<String>
        @RequestParam(required = false) String productDescription,
        @RequestParam(required = false) Long categoryId) {

    Set<Tag> tagSet = tags.stream()
                          .map(Tag::valueOf) // Conversión de String a Tag
                          .collect(Collectors.toSet());

    ProductDto updatedProduct = productService.updateProduct(productId, stock, discountPercentage, price, name, tagSet, productDescription, categoryId);
    return ResponseEntity.ok(updatedProduct);
}

    @GetMapping("/get")
    public List<ProductDto> getAll() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/get/{id}")
    public Optional<ProductDto> getProductById(@PathVariable("id") Long productId) {
        return productService.findById(productId);
    }

    @GetMapping("/get/tags")
    public Set<Tag> getTags() {
        return productService.getAllTags();
    }

    @PutMapping("/{productId}/removeTag")
    public ResponseEntity<ProductDto> removeTagFromProduct(
            @PathVariable Long productId, 
            @RequestParam Tag tag) {

        ProductDto updatedProduct = productService.removeTagFromProduct(productId, tag);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{productId}/removeImage/{imageId}")
    public ResponseEntity<ProductDto> removeImageFromProduct(
            @PathVariable Long productId, 
            @PathVariable Long imageId) {

        ProductDto updatedProduct = productService.removeImageFromProduct(productId, imageId);
        return ResponseEntity.ok(updatedProduct);
    }


    @GetMapping("/get/filter")
    public ResponseEntity<List<ProductDto>> filterProducts(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Set<Tag> tags) {

        List<ProductDto> filteredProducts = productService.filterProducts(
                Optional.ofNullable(minPrice).orElse(null), 
                Optional.ofNullable(maxPrice).orElse(null), 
                Optional.ofNullable(categoryId).orElse(null), 
                tags
        );

        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProductsByName(@RequestParam("name") String name) {
        return productService.findByProductName(name);
    }
    


}
