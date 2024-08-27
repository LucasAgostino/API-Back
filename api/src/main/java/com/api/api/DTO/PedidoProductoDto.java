package com.api.api.DTO;

import lombok.Data;

@Data
public class PedidoProductoDto {
    private Long idPedido;
    private String nombreProducto;
    private Integer cantidad;
    private float precioTotal;
}
