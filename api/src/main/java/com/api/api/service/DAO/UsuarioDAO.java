package com.api.api.service.DAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Usuario;
import com.api.api.repository.UsuarioRepository;

@Service
public class UsuarioDAO {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<UsuarioDto> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                       .map(UsuarioDAO::convertToDto)
                       .collect(Collectors.toList());
    }

    public Optional<UsuarioDto> findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioDAO::convertToDto);
    }

    public Optional<UsuarioDto> findByNombreUsuario(String nombreUsuario) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByNombreUsuario(nombreUsuario));
        return usuario.map(UsuarioDAO::convertToDto);
    }

    public static UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDto.setEmail(usuario.getMail());
        usuarioDto.setContrasena(usuario.getContrasena());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellido(usuario.getApellido());
        usuarioDto.setRol(usuario.getRol());
        return usuarioDto;
    }
}
