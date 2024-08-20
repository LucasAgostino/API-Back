package com.api.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Categoria;
import com.api.api.dominio.Producto;
import com.api.api.dominio.Usuario;
import com.api.api.repository.ProductoRepository;
import com.api.api.repository.UsuarioRepository;
import com.api.api.repository.CategoriaRepository;

@Service
public class ProductoService {
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

    public List<Producto> getProducto() {
        List<Producto> productos = productoRepository.findAll();
        return productos;
    }
    
}
