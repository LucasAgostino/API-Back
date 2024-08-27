package com.api.api.service.Interfaces;

import java.util.List;

import com.api.api.dominio.Categoria;

public interface CategoriaService {

    public List<Categoria> obtenerTodasLasCategorias();
    
    public Categoria crearCategoria(Categoria categoria);

    public Categoria obtenerCategoriaPorId(Long idCategoria);
}
