package com.api.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.dominio.PedidoProducto;

public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Long> {

    List<PedidoProducto> findByPedido_IdPedido(Long idPedido);
  
}
