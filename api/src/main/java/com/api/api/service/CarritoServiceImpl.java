package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Carrito;
import com.api.api.dominio.CarritoProducto;
import com.api.api.dominio.Pedido;
import com.api.api.dominio.Producto;
import com.api.api.dominio.Usuario;
import com.api.api.repository.CarritoProductoRepository;
import com.api.api.repository.CarritoRepository;
import com.api.api.repository.ProductoRepository;
import com.api.api.repository.UsuarioRepository;
import com.api.api.service.Interfaces.CarritoService;
import com.api.api.service.Interfaces.PedidoProductoService;
import com.api.api.service.Interfaces.PedidoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoProductoService pedidoProductoService;

    @Override
    public void crearCarrito(Long idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(LocalDateTime.now());
        carritoRepository.save(carrito);
    }
    @Override
    public String AgregarProducto(Long idProducto, Long idUsuario, int cantidad) {
        Producto producto = getProducto(idProducto);
        
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    
        CarritoProducto carritoProductoExistente = carritoProductoRepository.findByCarritoAndProducto(carrito, producto);
        
        if (carritoProductoExistente != null) {
            int stockViejo = carritoProductoExistente.getCantidad();
            if (!VerificarStock(producto, stockViejo + cantidad)) {
                return "No hay suficiente stock";
            }
            carritoProductoExistente.setCantidad(stockViejo + cantidad);
            carritoProductoExistente.setPrecioTotal(producto.getPrecio() * (stockViejo + cantidad));
        } else {
            CarritoProducto nuevoCarritoProducto = new CarritoProducto();
            if (!VerificarStock(producto, cantidad)) {
                return "No hay suficiente stock";
            }
            nuevoCarritoProducto.setCarrito(carrito);
            nuevoCarritoProducto.setProducto(producto);
            nuevoCarritoProducto.setCantidad(cantidad);
            nuevoCarritoProducto.setPrecioTotal(producto.getPrecio() * cantidad);
            carritoProductoRepository.save(nuevoCarritoProducto);
        }
    
        return "Producto agregado o actualizado en el carrito";
    }
    
    @Override
    public String eliminarProducto(Long idProducto, Long idUsuario) {
        Producto producto = getProducto(idProducto);
        
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        CarritoProducto carritoProducto = carritoProductoRepository.findByCarritoAndProducto(carrito, producto);
        
        if (carritoProducto != null) {
            carritoProductoRepository.delete(carritoProducto);
            return "Producto eliminado del carrito";
        } else {
            return "Producto no encontrado en el carrito";
        }
    }

    public String restarCarrito(Long idUsuario, Long idProducto, int cantidad){
        return "Producto restado del carrito";
    };

    @Override
    public void realizarPedido(Long idUsuario) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito);
        
        if (productos.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede realizar un pedido sin productos.");
        }
        
        float totalPedido = 0;
        
        for (CarritoProducto carritoProducto : productos) {
            Producto producto = carritoProducto.getProducto();
            int cantidad = carritoProducto.getCantidad();
            
            if (!VerificarStock(producto, cantidad)) {
                throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombreProducto());
            }
            
            totalPedido += carritoProducto.getPrecioTotal();
        }

        Pedido pedido = pedidoService.crearPedido(idUsuario, totalPedido);

        for (CarritoProducto carritoProducto : productos) {
            Producto producto = carritoProducto.getProducto();
            int cantidad = carritoProducto.getCantidad();
            
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
            pedidoProductoService.AgregarProducto(pedido.getIdPedido(), producto.getProductoId(), cantidad);
        }
        
        carritoProductoRepository.deleteAll(productos);
    }
  
    @Override
    public List<CarritoProducto> findByCarrito(Long idUsuario) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        return carritoProductoRepository.findByCarrito(carrito);
    }


    
    // Funciones Privadas
    private Producto getProducto(Long idProducto){
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (!productoOpt.isPresent()) {
            return null;
        }
        return productoOpt.get();
    }

    private boolean VerificarStock(Producto producto, int cantidad){
        return producto.getStock() >= cantidad;
    }
    private void vaciarCarrito(Carrito carrito) {
        carritoProductoRepository.deleteByCarrito(carrito);
    }
    
    
}

