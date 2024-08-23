package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.repository.ProductoRepository;

import java.util.List;
import java.util.stream.Collectors;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Producto;

@Service
public class ProductoDao {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<ProductoDto> findAll() {
        List<Producto> productos = productoRepository.findAllProductosActivos();
        return productos.stream().map(this::convertToDTO).collect(Collectors.toList());    
    }

    @Transactional(readOnly = true)
    public ProductoDto findById(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        return convertToDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoDto> findByPrecioBetween(Float precioMin, Float precioMax) {
        List<Producto> productos = productoRepository.findByPrecioBetween(precioMin, precioMax);
        return productos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDto> getProductosPorCategoria(Long categoriaId) {
        List<Producto> productos = productoRepository.findAllProductosByCategoria(categoriaId);
        return productos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private ProductoDto convertToDTO(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setProductoId(producto.getProductoId());
        dto.setNombre(producto.getNombreProducto());
        dto.setPrecio(producto.getPrecio());
        dto.setImagen(producto.getImagen());
        return dto;
    }
}