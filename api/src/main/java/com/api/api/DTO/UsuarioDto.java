package com.api.api.DTO;


import com.api.api.dominio.Rol;

import lombok.Data;

@Data
public class UsuarioDto {
    private String userName;
    private String password;
    private String firstName;
    private String secondName;
    private Rol role;

}

