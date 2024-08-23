package com.api.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.service.DAO.ProductoDao;
import com.api.api.service.Interfaces.ProductoService;
import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Categoria;
import com.api.api.dominio.Producto;
import com.api.api.dominio.Usuario;
import com.api.api.repository.ProductoRepository;
import com.api.api.repository.UsuarioRepository;
import com.api.api.repository.CategoriaRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public Producto crearProducto(Producto producto){
        Usuario usuario = usuarioRepository.findById(producto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
        producto.setUsuario(usuario);

        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoria not found"));
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    public Producto darBajaProducto(Long productoId){
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        producto.setEstado(false);
        return productoRepository.save(producto);
    }

    public Producto modificarStockProducto(Long productoId, int cantidad){
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        producto.setStock(cantidad);
        return productoRepository.save(producto);
    }

    public List<ProductoDto> getProducto() {
        return productoDao.findAll();
    }

    public Optional<Producto> findById(Long productoId) {
        return productoRepository.findById(productoId);
    }

    public List<ProductoDto> getProductosPorCategoria(Long categoriaId) {
        return productoDao.getProductosPorCategoria(categoriaId);
    }
    
    public List<ProductoDto> findByPrecioBetween(Float precioMin, Float precioMax) { 
        return productoDao.findByPrecioBetween(precioMin, precioMax);
    }
}
