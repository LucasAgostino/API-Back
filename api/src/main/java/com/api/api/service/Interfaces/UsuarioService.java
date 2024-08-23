package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.api.DTO.UsuarioDto;
import com.api.api.dominio.Usuario;
public interface UsuarioService {
    public Usuario registrarUsuario(Usuario usuario, String rol);

    public Usuario autenticarUsuario(String username, String password);
    
    public List<UsuarioDto> findAll();

    public Optional<UsuarioDto> findById(Long id);

    public Optional<UsuarioDto> findByNombreUsuario(String nombreUsuario);
}
