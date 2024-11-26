package com.api.api.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;

import java.util.ArrayList; // Add this import statement
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductDto;
import com.api.api.entity.Category;
import com.api.api.entity.Product;
import com.api.api.entity.Tag;
import com.api.api.entity.ProductImage;
import com.api.api.repository.CategoryRepository;
import com.api.api.repository.ProductImagesRepository;
import com.api.api.repository.ProductRepository;
import com.api.api.service.DAO.ProductDao;
import com.api.api.service.Interfaces.CartService;
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

    @Autowired
    private CartService cartService;

    @Override
    public ProductDto createProduct(String productName, String productDescription, float price, float discountPercentage, int stock, List<MultipartFile> images, Long categoryId, Set<Tag> tags) {
        // Verificar y establecer la categoría
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        // Crear un nuevo objeto Product
        Product product = new Product();
        product.setProductName(productName);
        product.setDescriptionProducto(productDescription);
        product.setPrice(price);
        product.setDiscountPercentage(discountPercentage);
        product.setStock(stock);
        product.setCategory(category);
        product.setTags(tags);
        
        // Guardar el producto primero
        Product savedProduct = productRepository.save(product);
        System.out.println("Product saved with ID: " + savedProduct.getProductId());
        
        // Manejar las imágenes: Usar la función para agregar imágenes al producto ya guardado
        if (images != null && !images.isEmpty()) {
            System.out.println("Processing images...");
            addImagesToProduct(savedProduct.getProductId(), images);
        } else {
            System.out.println("No images to process.");
        }
        
        // Devolver el producto con las imágenes
        return productDao.findById(savedProduct.getProductId());
    }
    
    
    
   @Override
    public ProductDto addImagesToProduct(Long productId, List<MultipartFile> images) {
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
                        Blob blob = new SerialBlob(image.getBytes()); // Convertir byte[] a Blob
                        productImage.setImageData(blob);
                        productImage.setProduct(product); 
                        productImages.add(productImage);
                        productImagesRepository.save(productImage);
                        System.out.println("Image processed and added: " + image.getOriginalFilename());
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException("Error processing the image", e);
                    }
                } else {
                    System.out.println("Empty or null image file skipped.");
                }
            }
            if (product.getImages() == null) {
                product.setImages(new ArrayList<>());
            }
            product.getImages().addAll(productImages); // Agregar nuevas imágenes al producto existente
        } else {
            System.out.println("No images provided.");
        }
        productRepository.save(product);
        // Guardar y devolver el producto actualizado
        return productDao.findById(productId);
    }
    

    @Override
    public ProductDto removeTagFromProduct(Long productId, Tag tag) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Eliminar el tag del conjunto si está presente
        product.getTags().remove(tag);

        // Guardar los cambios
        Product updatedProduct = productRepository.save(product);

        // Devolver el producto actualizado
        return productDao.findById(updatedProduct.getProductId());
    }




    @Override
    public ProductDto softDeleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setState(false);
        productRepository.save(product);
        return productDao.findById(productId);
    }

    @Override
    public ProductDto updateProduct(Long productId, Integer stock, Float discountPercentage, Float price, String name, Set<Tag> tag, String description, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Actualizar stock si es proporcionado
        if (stock != null) {
            product.setStock(stock);
        }

        // Actualizar descuento si es proporcionado
        if (discountPercentage != null) {
            product.setDiscountPercentage(discountPercentage);
            cartService.updatePricesInCarts(product);
        }

        // Actualizar precio si es proporcionado
        if (price != null) {
            product.setPrice(price);
            cartService.updatePricesInCarts(product); // Si necesitas actualizar precios en el carrito
        }

        // Actualizar nombre si es proporcionado
        if (name != null) {
            productRepository.findByProductName(name).ifPresent(p -> {
                throw new RuntimeException("Product with the same name already exists");
            });
            product.setProductName(name);
        }

        // Agregar tag si es proporcionado
if (tag != null) {
    // Reemplazar las etiquetas del producto con la nueva lista
    product.setTags(new HashSet<>(tag)); // Asumiendo que product.getTags() devuelve un Set<Tag>
}



        // Actualizar descripción si es proporcionada
        if (description != null) {
            product.setDescriptionProducto(description);
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        // Guardar cambios en el repositorio
        productRepository.save(product);

        // Devolver el producto actualizado
        return productDao.findById(productId);
    }


    @Override
    public ProductDto removeImageFromProduct(Long productId, Long ImageId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductImage productImage = productImagesRepository.findById(ImageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        product.getImages().remove(productImage);
        productRepository.save(product);
        productImagesRepository.delete(productImage);
        return productDao.findById(productId);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public Optional<ProductDto> findById(Long productId) {
        return Optional.ofNullable(productDao.findById(productId));
    }

    @Override
    public Set<Tag> getAllTags() {
        return Tag.getAllTags();
    }

    @Override
    public List<ProductDto> filterProducts(Float minPrice, Float maxPrice, Long categoryId, Set<Tag> tags) {
        return productDao.filterProducts(minPrice, maxPrice, categoryId, tags);
    }

    @Override
    public List<ProductDto> findByProductName(String productName) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        
        // Convertir a DTO antes de devolver
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(productDao.findById(product.getProductId()));
        }
        
        return productDtos;
    }


}
