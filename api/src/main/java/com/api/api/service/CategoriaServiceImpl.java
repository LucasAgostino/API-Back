package com.api.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Categoria;
import com.api.api.exception.CategoriaYaExisteException;
import com.api.api.repository.CategoriaRepository;
import com.api.api.service.Interfaces.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
    if (categoriaRepository.existsByNombreCategoria(categoria.getNombreCategoria())) {
        throw new CategoriaYaExisteException("La categoría ya existe");
    }
    return categoriaRepository.save(categoria);
    }


    @Override
    public Categoria obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
