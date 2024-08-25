package com.api.api.service.Interfaces;

import com.api.api.dominio.Pedido;

public interface PedidoService {
    public Pedido crearPedido(Long idUsuario, float total);
}
