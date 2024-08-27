package com.api.api.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class PedidoDto {
    private Long idPedido;
    private Long idUsuario;
    private LocalDateTime fechaPedido;
    private float totalPedido;
    private Set<PedidoProductoDto> pedidoProductos;
}

