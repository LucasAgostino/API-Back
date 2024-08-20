package com.api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.dominio.Usuario;
import com.api.api.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/asignarRol")
    public ResponseEntity<?> asignarRol(@RequestParam String nombreUsuario, @RequestParam String rolNombre) {
        usuarioService.asignarRol(nombreUsuario, rolNombre);
        return ResponseEntity.ok("Rol asignado exitosamente");
    }

    // Endpoint POST para obtener todos los usuarios
    @GetMapping("/todos")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }
}

