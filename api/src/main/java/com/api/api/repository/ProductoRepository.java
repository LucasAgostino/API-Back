package com.api.api.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.api.dominio.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.stock > 0 AND p.estado = true")
    List<Producto> findAllProductosActivos();

    // Query para poder filtrar por categoria
    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :categoriaId")
    List<Producto> findAllProductosByCategoria(@Param("categoriaId") Long categoriaId);

    @Query("SELECT p FROM Producto p WHERE " +
        "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
        "(:precioMax IS NULL OR p.precio <= :precioMax)")
        List<Producto> findByPrecioBetween(@Param("precioMin") Float precioMin, 
                                            @Param("precioMax") Float precioMax);
}