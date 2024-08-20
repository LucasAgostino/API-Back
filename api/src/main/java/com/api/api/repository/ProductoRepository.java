package com.api.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.api.dominio.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    
}