package com.api.api.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioDto {
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombre;
    private String apellido;
    private List<String> roles;

}

