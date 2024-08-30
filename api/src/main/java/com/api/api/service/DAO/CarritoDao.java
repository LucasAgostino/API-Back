package com.api.api.service.DAO;

import com.api.api.DTO.CarritoDto;
import com.api.api.DTO.CarritoProductoDto;
import com.api.api.dominio.Carrito;
import com.api.api.dominio.CarritoProducto;
import com.api.api.repository.CarritoProductoRepository;
import com.api.api.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoDao {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Transactional(readOnly = true)
    public Optional<CarritoDto> findByCarritoId(Long idCarrito) {
        Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);
        return carritoOpt.map(this::convertToCarritoDto);
    }

    private CarritoProductoDto convertToCarritoProductoDto(CarritoProducto carritoProducto) {
        CarritoProductoDto dto = new CarritoProductoDto();
        dto.setIdCarritoProducto(carritoProducto.getIdCarritoProducto());
        dto.setProducto(carritoProducto.getProducto()); // Esto trae todo el objeto Producto
        dto.setCantidad(carritoProducto.getCantidad());
        dto.setPrecioTotal(carritoProducto.getPrecioTotal());
        return dto;
    }

    private CarritoDto convertToCarritoDto(Carrito carrito) {
        CarritoDto dto = new CarritoDto();
        dto.setIdCarrito(carrito.getIdCarrito());
    
        List<CarritoProducto> carritoProductos = carritoProductoRepository.findByCarrito(carrito);
    
        List<CarritoProductoDto> productosDto = carritoProductos.stream()
                .map(this::convertToCarritoProductoDto)
                .collect(Collectors.toList());
    
        dto.setProductos(productosDto);
        return dto;
    }
    
}
