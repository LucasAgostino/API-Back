package com.api.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.dominio.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {    
    List<Pedido> findByUsuarioId(Long idUsuario);
}