package com.api.api.service.Interfaces;

import java.util.List;
import com.api.api.dominio.Usuario;
public interface UsuarioService {
    public Usuario registrarUsuario(Usuario usuario, List<String> roles);
    public Usuario autenticarUsuario(String username, String password);
    public void asignarRol(String nombreUsuario, String rolNombre);
    public List<Usuario> findAll();
}
