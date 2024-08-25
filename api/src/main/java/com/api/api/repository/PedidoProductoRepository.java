package com.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.dominio.PedidoProducto;

public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Long> {
  
}
