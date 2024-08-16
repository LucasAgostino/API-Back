package com.api.api.controller;

import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Usuario;
import com.api.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String showRegistrationForm() {
        return "register"; // Asegúrate de que este nombre coincida con el nombre del archivo de la plantilla HTML
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario(
            usuarioDto.getNombreUsuario(),
            usuarioDto.getEmail(),
            usuarioDto.getContrasena(),
            usuarioDto.getNombre(),
            usuarioDto.getApellido(),
            usuarioDto.getRoles()
            );

        try {
            usuarioService.registrarUsuario(usuario, usuarioDto.getRoles());
            return ResponseEntity.ok("Usuario registrado exitosamente"); // Respuesta exitosa
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el usuario"); // Respuesta de error
        }
    }
}
