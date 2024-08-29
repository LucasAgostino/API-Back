package com.api.api.service.DAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Usuario;
import com.api.api.repository.UsuarioRepository;

@Service
public class UsuarioDAO {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional (readOnly = true)
    public List<UsuarioDto> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                       .map(UsuarioDAO::convertToDto)
                       .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public Optional<UsuarioDto> findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioDAO::convertToDto);
    }

    @Transactional (readOnly = true)
    public Optional<UsuarioDto> findByNombreUsuario(String nombreUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(nombreUsuario);
        return usuario.map(UsuarioDAO::convertToDto);
    }

    private static UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUserName(usuario.getEmail());
        usuarioDto.setPassword(usuario.getPassword());
        usuarioDto.setFirstName(usuario.getFirstName());
        usuarioDto.setSecondName(usuario.getSecondName());
        usuarioDto.setRole(usuario.getRol());
        return usuarioDto;
    }
}
