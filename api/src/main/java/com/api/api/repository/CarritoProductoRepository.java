package com.api.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.api.api.dominio.Carrito;
import com.api.api.dominio.CarritoProducto;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long>{
    
    @Query("SELECT cp FROM CarritoProducto cp WHERE cp.carrito.idCarrito = :idCarrito AND cp.producto.productoId = :idProducto")
    Optional<CarritoProducto> findByCarritoIdAndProductoId(@Param("idCarrito") Long idCarrito, @Param("idProducto") Long idProducto);

    // Método para encontrar todos los productos en un carrito específico
    List<CarritoProducto> findByCarrito(Carrito carrito);
}
