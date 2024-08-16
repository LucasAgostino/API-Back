package com.api.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.dominio.Usuario;
import com.api.api.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario, List<String> roles) {
        // Verificar unicidad del nombre de usuario
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("Nombre de usuario ya en uso.");
        }

        // Verificar unicidad del correo electrónico
        if (usuarioRepository.existsByMail(usuario.getMail())) {
            throw new RuntimeException("Correo electrónico ya en uso.");
        }

        // Codificar la contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        
        // Asignar roles directamente como lista de strings
        if (roles == null) {
            roles = new ArrayList<>();
        }
        usuario.setRoles(roles);

        // Guardar usuario
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username);
        if (usuario != null) {
            if (passwordEncoder.matches(password, usuario.getContrasena())) {
                return usuario;
            } else {
                throw new RuntimeException("Contraseña incorrecta");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void asignarRol(String nombreUsuario, String rolNombre) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario != null) {
            List<String> roles = usuario.getRoles();
            if (!roles.contains(rolNombre)) {
                roles.add(rolNombre);
                usuario.setRoles(roles);
                usuarioRepository.save(usuario);
            } else {
                throw new RuntimeException("El usuario ya tiene el rol asignado.");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
