package com.api.api.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(name= "producto_id")
    private Long productoId;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "precio")
    private float precio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "stock")
    private int stock;

    @Column(name = "imagen")
    private String imagen;
}
