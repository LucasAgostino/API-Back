package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Producto;
import com.api.api.service.Interfaces.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/create")
    public Producto postMethodName(@RequestParam("nombreProducto") String nombreProducto,
        @RequestParam("descripcionProducto") String descripcionProducto,
        @RequestParam("precio") float precio,
        @RequestParam("stock") int stock,
        @RequestParam("imagen") MultipartFile imagen,
        @RequestParam("categoriaId") Long categoriaId) { 
            return productoService.crearProducto(nombreProducto, descripcionProducto, precio, stock, imagen, categoriaId);
    }

    // Realiza una baja logica de un producto
    @PostMapping("/delete/{id}")
    public Producto darBajaProducto(@PathVariable("id") Long productoId) {
        return productoService.darBajaProducto(productoId);
    }

    // Modifica el stock de un producto
    @PostMapping("/update/{id}")
    public Producto modificarStockProducto(@PathVariable("id") Long productoId, @RequestParam int cantidad) {
        return productoService.modificarStockProducto(productoId, cantidad);
    }
    
    // Trae unicamente el id, nombre, precio y foto
    @GetMapping("/get")
    public List<ProductoDto> postMethodName() {
        return productoService.getProducto();
    }
    
    // Trae todos los detalles sobre ese producto
    @GetMapping("/get/{id}")
    public Optional<Producto> getMethodName(@PathVariable("id") Long productoId) {
        return productoService.findById(productoId);
    }

    // Muestra filtrado todos los productos por categoria
    @GetMapping("/get/cat/{categoria}")
    public List<ProductoDto> getporCategoria(@PathVariable("categoria") Long categoriaId) {
        return productoService.getProductosPorCategoria(categoriaId);
    }

    // Muestra filtrado todos los productos por precio
    @GetMapping("/get/filtrar")
    public List<ProductoDto> filtrarProductosPorPrecio(@RequestParam(required = false) Float precioMin,
                                                    @RequestParam(required = false) Float precioMax) {
        return productoService.findByPrecioBetween(precioMin, precioMax);
    }

    @GetMapping("/get/{id}/imagen")
    public ResponseEntity<byte[]> getImagen(@PathVariable Long id) {
        Optional<Producto> productoOpt = productoService.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            byte[] imagen = producto.getImagen();

            if (imagen != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el formato de la imagen
                return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Imagen no encontrada
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Producto no encontrado
        }
    }

}
