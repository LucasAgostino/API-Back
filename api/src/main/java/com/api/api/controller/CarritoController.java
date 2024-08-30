package com.api.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.api.api.DTO.CarritoDto;
import com.api.api.service.Interfaces.CarritoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
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
    
    @GetMapping
    public Optional<CarritoDto> getCarrito(@RequestParam Long idUsuario) {
        return carritoService.findByCarrito(idUsuario);
    }
    
    @PostMapping("/eliminar")
    public ResponseEntity<String> eliminarProducto(
            @RequestParam Long idProducto,
            @RequestParam Long idUsuario) {
        String mensaje = carritoService.eliminarProducto(idProducto, idUsuario);
        return ResponseEntity.ok(mensaje);
    }
        
    @PostMapping("/restar")
        public ResponseEntity<String> restarCarrito(
            @RequestParam Long idProducto,
            @RequestParam Long idUsuario,
            @RequestParam int cantidad) {
        String mensaje = carritoService.restarCarrito(idUsuario, idProducto, cantidad);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/vaciar")
    public void vaciarCarrito(@RequestParam Long idUsuario) {
        carritoService.realizarPedido(idUsuario);
    }
        
}
