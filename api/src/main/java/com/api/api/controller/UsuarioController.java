package com.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.DTO.UsuarioDto;
import com.api.api.service.Interfaces.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/asignarRol")
    public ResponseEntity<?> asignarRol(@RequestParam String nombreUsuario, @RequestParam String rolNombre) {
        return ResponseEntity.ok("Rol asignado exitosamente");
    }

    // Endpoint POST para obtener todos los usuarios
    @GetMapping("/get")
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<UsuarioDto> getById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @GetMapping("/get/bynombre")
    public Optional<UsuarioDto> getByNombreUsuario(@RequestParam String nombreUsuario) {
        return usuarioService.findByNombreUsuario(nombreUsuario);
    }
    
    
}

