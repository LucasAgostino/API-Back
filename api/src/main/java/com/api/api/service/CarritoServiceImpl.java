package com.api.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Carrito;
import com.api.api.dominio.CarritoProducto;
import com.api.api.dominio.Producto;
import com.api.api.repository.CarritoProductoRepository;
import com.api.api.repository.CarritoRepository;
import com.api.api.repository.ProductoRepository;
import com.api.api.service.Interfaces.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    public String AgregarProducto(Long idProducto, Long idUsuario, int cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (!productoOpt.isPresent()) {
            return "Producto no encontrado";
        }
        
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Producto producto = productoOpt.get();
        Carrito carrito = carritoOpt.get();
        
        Optional<CarritoProducto> carritoproductoOpt = carritoProductoRepository.findByCarritoIdAndProductoId(carrito.getIdCarrito(), idProducto);
        if (carritoproductoOpt.isPresent()) {
            CarritoProducto carritoProducto = carritoproductoOpt.get();
            int stockViejo = carritoProducto.getCantidad();
            if(!VerificarStock(producto, stockViejo + cantidad)){
                return "No hay suficiente stock";
            }
            carritoProducto.setCantidad(stockViejo + cantidad); // Usar setCantidad en lugar de Setcantidad
            carritoProducto.setPrecioTotal(producto.getPrecio() * (stockViejo + cantidad)); // Actualizar precio total
            carritoProductoRepository.save(carritoProducto); // Guardar los cambios
            return "Producto actualizado en el carrito";
        }
        
        CarritoProducto nuevoCarritoProducto = new CarritoProducto();
        if(!VerificarStock(producto, cantidad)){
            return "No hay suficiente stock";
        }
        nuevoCarritoProducto.setCantidad(cantidad);
        nuevoCarritoProducto.setCarrito(carrito);
        nuevoCarritoProducto.setProducto(producto);
        nuevoCarritoProducto.setPrecioTotal(producto.getPrecio() * cantidad);
        carritoProductoRepository.save(nuevoCarritoProducto);
        return "Producto agregado al carrito";
    }
    private boolean VerificarStock(Producto producto, int cantidad){
        if(producto.getStock() >= cantidad){
            return true;
        }
        return false;
    }
}

