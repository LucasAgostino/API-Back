package com.api.api.service.Interfaces;


import com.api.api.DTO.CarritoDto;

public interface CarritoService {

    public void crearCarrito(Long idUsuario);

    public String AgregarProducto (Long idProducto, Long idUsuario, int cantidad);

    public CarritoDto findByCarrito(Long idUsuario);

    public String eliminarProducto(Long idProducto, Long idUsuario);

    public String restarCarrito(Long idUsuario, Long idProducto, int cantidad);

    public void realizarPedido(Long idUsuario);
}
