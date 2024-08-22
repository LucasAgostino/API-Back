package com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.api.service.Interfaces.CarritoService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarProducto(
            @RequestParam Long idProducto,
            @RequestParam Long idUsuario,
            @RequestParam int cantidad) {
        String mensaje = carritoService.AgregarProducto(idProducto, idUsuario, cantidad);
        return ResponseEntity.ok(mensaje);
    }
    
    
}
