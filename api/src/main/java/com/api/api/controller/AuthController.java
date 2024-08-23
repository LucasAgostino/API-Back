package com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Usuario;
import com.api.api.service.UsuarioServiceImpl;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping
    public String showRegistrationForm() {
        return "register"; // Asegúrate de que este nombre coincida con el nombre del archivo de la plantilla HTML
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registerUser(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario(
            usuarioDto.getNombreUsuario(),
            usuarioDto.getEmail(),
            usuarioDto.getContrasena(),
            usuarioDto.getNombre(),
            usuarioDto.getApellido(),
            usuarioDto.getRol()
            );

        try {
            usuarioService.registrarUsuario(usuario, usuarioDto.getRol());
            return ResponseEntity.ok("Usuario registrado exitosamente"); // Respuesta exitosa
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el usuario"); // Respuesta de error
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        Usuario usuario = usuarioService.autenticarUsuario(username, password);
        return ResponseEntity.ok(usuario);
    }
}
