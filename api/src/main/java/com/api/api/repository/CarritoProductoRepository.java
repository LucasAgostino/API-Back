package com.api.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.api.dominio.CarritoProducto;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long>{
    
    @Query("SELECT cp FROM CarritoProducto cp WHERE cp.carrito.idCarrito = :idCarrito AND cp.producto.productoId = :idProducto")
    Optional<CarritoProducto> findByCarritoIdAndProductoId(@Param("idCarrito") Long idCarrito, @Param("idProducto") Long idProducto);
}
