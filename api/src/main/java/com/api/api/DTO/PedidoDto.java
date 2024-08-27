package com.api.api.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class PedidoDto {
    private Long idPedido;
    private String nombreUsuario;
    private LocalDateTime fechaPedido;
    private float totalPedido;
    private Set<PedidoProductoDto> pedidoProductos; // Ensure this is populated
}
