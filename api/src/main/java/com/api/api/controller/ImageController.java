package com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Blob;
import java.sql.SQLException;

import com.api.api.entity.ProductImage; // Asegúrate de que la ruta sea correcta para tu entidad ProductImage
import com.api.api.repository.ProductImagesRepository;


@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ProductImagesRepository productImageRepository; // Asegúrate de tener el repositorio para acceder a las imágenes

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        ProductImage productImage = productImageRepository.findById(imageId).orElse(null);
        if (productImage == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Blob blob = productImage.getImageData();
            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // O el tipo adecuado según la imagen
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
