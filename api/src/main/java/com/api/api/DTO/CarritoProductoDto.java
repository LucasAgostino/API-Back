package com.api.api.DTO;

import com.api.api.dominio.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoProductoDto {
    private Long idCarritoProducto;
    private Producto producto; // Se puede elegir tambien para en vez de traer el objeto completo traer unicamente el nombre o algo asi
    private int cantidad;
    private float precioTotal;
}
