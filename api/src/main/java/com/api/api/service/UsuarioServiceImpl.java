package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Carrito;
import com.api.api.dominio.Usuario;
import com.api.api.repository.CarritoRepository;
import com.api.api.repository.UsuarioRepository;
import com.api.api.service.DAO.UsuarioDAO;
import com.api.api.service.Interfaces.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public Usuario registrarUsuario(Usuario usuario, String rol) {
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
        
       
        usuario.setRol(rol);
        
        // Guardar usuario
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Crear un carrito vacío y asociarlo al usuario guardado
        Carrito carrito = new Carrito();
        carrito.setUsuario(savedUsuario); // Usa el usuario guardado
        carrito.setFechaCreacion(LocalDateTime.now());

        carritoRepository.save(carrito); // Guarda el carrito

        return savedUsuario;
        }

    @Override
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



    @Override
    public List<UsuarioDto> findAll() {
        return usuarioDAO.findAll();
    }

     @Override
    public Optional<UsuarioDto> findById(Long id) {
        return usuarioDAO.findById(id);
    }

    @Override
    public Optional<UsuarioDto> findByNombreUsuario(String nombreUsuario) {
        return usuarioDAO.findByNombreUsuario(nombreUsuario);
    }
}
