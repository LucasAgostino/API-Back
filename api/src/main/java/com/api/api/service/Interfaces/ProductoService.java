package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Producto;

public interface ProductoService {
    
    public Producto crearProducto(Producto producto);

    public List<ProductoDto> getProducto();

    public Optional<Producto> findById(Long productoId);

    public List<ProductoDto> getProductosPorCategoria(Long categoriaId);
    
    public List<ProductoDto> findByPrecioBetween(Float precioMin, Float precioMax);
}
