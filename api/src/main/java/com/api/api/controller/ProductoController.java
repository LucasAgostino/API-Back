package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Producto;
import com.api.api.service.Interfaces.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/create")
    public Producto postMethodName(@RequestBody Producto producto) { 
        return productoService.crearProducto(producto);
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
}
