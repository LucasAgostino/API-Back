package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Producto;

public interface ProductoService {
    
    public Producto crearProducto(String nombreProducto, String descripcionProducto, float precio, int stock, MultipartFile imagen, Long categoriaId);

    public Producto darBajaProducto(Long productoId);

    public Producto modificarStockProducto(Long productoId, int cantidad);

    public List<ProductoDto> getProducto();

    public Optional<Producto> findById(Long productoId);

    public List<ProductoDto> getProductosPorCategoria(Long categoriaId);
    
    public List<ProductoDto> findByPrecioBetween(Float precioMin, Float precioMax);
}
