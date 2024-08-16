package com.api.api.dominio;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    @Column(unique = true, nullable = false)
    private String mail;

    private String contrasena;

    private String nombre;

    private String apellido;
    
    @ElementCollection
    @Column(name = "roles")
    private List<String> roles;

    // Puedes agregar un constructor con parámetros si lo necesitas
    public Usuario(String nombreUsuario, String mail, String contrasena, String nombre, String apellido, List<String> roles) {
        this.nombreUsuario = nombreUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.roles = roles;
    }
}
