package com.api.api.service.Interfaces;

import java.util.List;

import com.api.api.dominio.CarritoProducto;

public interface CarritoService {

    public void crearCarrito(Long idUsuario);

    public String AgregarProducto (Long idProducto, Long idUsuario, int cantidad);

    public List<CarritoProducto> findByCarrito(Long idUsuario);

    public String eliminarProducto(Long idProducto, Long idUsuario);

    public String restarCarrito(Long idUsuario, Long idProducto, int cantidad);

    public void realizarPedido(Long idUsuario);
}
