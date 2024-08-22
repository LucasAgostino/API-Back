package com.api.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.dominio.Carrito;
public interface CarritoRepository extends JpaRepository<Carrito, Long>{
    // Método para encontrar un carrito por el id del usuario
    Optional<Carrito> findByUsuarioId(Long idUsuario);
    
}