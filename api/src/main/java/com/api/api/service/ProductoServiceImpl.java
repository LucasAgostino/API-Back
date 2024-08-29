package com.api.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.api.DTO.ProductoDto;
import com.api.api.dominio.Categoria;
import com.api.api.dominio.Producto;
import com.api.api.repository.CategoriaRepository;
import com.api.api.repository.ProductoRepository;
import com.api.api.service.DAO.ProductoDao;
import com.api.api.service.Interfaces.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Override
    public Producto crearProducto(String nombreProducto, String descripcionProducto, float precio, int stock, MultipartFile imagen, Long categoriaId) {
    // Verifica y establece la categoría
        Categoria categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new RuntimeException("Categoria not found"));

        // Crea un nuevo objeto Producto
        Producto producto = new Producto();
        producto.setNombreProducto(nombreProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        // Manejo de la imagen: Convierte el MultipartFile a byte[]
        if (imagen != null && !imagen.isEmpty()) {
                try {
                    producto.setImagen(imagen.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Error procesando la imagen", e);
                }
        }

        // Guarda y retorna el producto
        return productoRepository.save(producto);
    }


    @Override
    public Producto darBajaProducto(Long productoId){
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        producto.setEstado(false);
        return productoRepository.save(producto);
    }

    @Override
    public Producto modificarStockProducto(Long productoId, int cantidad){
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        producto.setStock(cantidad);
        return productoRepository.save(producto);
    }

    @Override
    public List<ProductoDto> getProducto() {
        return productoDao.findAll();
    }

    @Override
    public Optional<Producto> findById(Long productoId) {
        return productoRepository.findById(productoId);
    }

    @Override
    public List<ProductoDto> getProductosPorCategoria(Long categoriaId) {
        return productoDao.getProductosPorCategoria(categoriaId);
    }
    
    @Override
    public List<ProductoDto> findByPrecioBetween(Float precioMin, Float precioMax) { 
        return productoDao.findByPrecioBetween(precioMin, precioMax);
    }
}
