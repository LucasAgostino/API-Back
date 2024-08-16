package com.api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.api.dominio.Usuario;
import com.api.api.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/auth/login")
    public ResponseEntity<Usuario> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        Usuario usuario = usuarioService.autenticarUsuario(username, password);
        return ResponseEntity.ok(usuario);
    }


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

