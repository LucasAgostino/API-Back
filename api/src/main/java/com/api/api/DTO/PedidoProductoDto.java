package com.api.api.DTO;

import lombok.Data;

@Data
public class PedidoProductoDto {
    private Long idPedidoProducto;
    private Long idPedido;
    private Long idProducto;
    private Integer cantidad;
    private float precioTotal;
}
