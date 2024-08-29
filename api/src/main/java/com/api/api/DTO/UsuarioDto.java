package com.api.api.DTO;


import com.api.api.dominio.Rol;

import lombok.Data;

@Data
public class UsuarioDto {
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombre;
    private String apellido;
    private Rol rol;

}

