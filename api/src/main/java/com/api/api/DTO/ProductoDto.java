package com.api.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoDto {
    private Long productoId;
    private String nombre;
    private float precio;
    private String imagen;
}