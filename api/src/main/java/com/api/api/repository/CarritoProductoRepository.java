package com.api.api.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.api.api.dominio.Carrito;
import com.api.api.dominio.CarritoProducto;
import com.api.api.dominio.Producto;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long>{


    // Método para encontrar todos los productos en un carrito específico
    List<CarritoProducto> findByCarrito(Carrito carrito);

    CarritoProducto findByCarritoAndProducto(Carrito carrito, Producto producto);
}
