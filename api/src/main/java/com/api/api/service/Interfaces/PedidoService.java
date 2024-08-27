package com.api.api.service.Interfaces;


import java.util.Optional;

import java.util.List;

import com.api.api.DTO.PedidoDto;
import com.api.api.dominio.Pedido;

public interface PedidoService {
    public List<PedidoDto> findAll();
    public Optional<PedidoDto> findById(Long id);
    public List<PedidoDto> findByUsuarioId(Long idUsuario);
    public Pedido crearPedido(Long idUsuario, float total);
}
