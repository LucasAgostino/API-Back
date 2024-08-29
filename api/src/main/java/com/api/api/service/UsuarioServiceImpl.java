package com.api.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.api.DTO.UsuarioDto;
import com.api.api.service.DAO.UsuarioDAO;
import com.api.api.service.Interfaces.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;



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
