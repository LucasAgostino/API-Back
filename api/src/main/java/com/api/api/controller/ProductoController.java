package com.api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.dominio.Producto;
import com.api.api.service.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/get")
    public List<Producto> postMethodName() {
        return productoService.getProducto();
    }
    
}
