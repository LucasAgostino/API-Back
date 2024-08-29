package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.api.DTO.UsuarioDto;
public interface UsuarioService {
    
    public List<UsuarioDto> findAll();

    public Optional<UsuarioDto> findById(Long id);

    public Optional<UsuarioDto> findByNombreUsuario(String nombreUsuario);
}
