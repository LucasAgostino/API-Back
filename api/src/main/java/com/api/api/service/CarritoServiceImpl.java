package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.DTO.CarritoDto;
import com.api.api.DTO.CarritoProductoDto;
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
            carritoProductoRepository.save(carritoProductoExistente);
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

    @Override
    public String restarCarrito(Long idUsuario, Long idProducto, int cantidad) {
        // Obtener el carrito del usuario
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Obtener el producto en el carrito
        Producto producto = getProducto(idProducto);
        CarritoProducto carritoProductoExistente = carritoProductoRepository.findByCarritoAndProducto(carrito, producto);

        if (carritoProductoExistente != null) {
            int cantidadActual = carritoProductoExistente.getCantidad();

            // Verificar si la cantidad a restar es válida
            if (cantidadActual < cantidad) {
                return "No se puede restar más cantidad de la que hay en el carrito";
            }

            // Restar la cantidad
            int nuevaCantidad = cantidadActual - cantidad;

            if (nuevaCantidad > 0) {
                // Actualizar la cantidad y el precio total
                carritoProductoExistente.setCantidad(nuevaCantidad);
                carritoProductoExistente.setPrecioTotal(producto.getPrecio() * nuevaCantidad);
                carritoProductoRepository.save(carritoProductoExistente);
            } else {
                // Si la nueva cantidad es 0 o menor, eliminar el producto del carrito
                carritoProductoRepository.delete(carritoProductoExistente);
            }

            return "Producto restado del carrito";
        }

        return "Producto no encontrado en el carrito";
    }


    @Override
    public void realizarPedido(Long idUsuario) {
        // Obtener el carrito del usuario
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        // Obtener todos los productos del carrito
        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito);
        
        // Verificar que el carrito no esté vacío
        if (productos.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede realizar un pedido sin productos.");
        }
        
        float totalPedido = 0;
        
        // Verificar stock y calcular el total del pedido
        for (CarritoProducto carritoProducto : productos) {
            Producto producto = carritoProducto.getProducto();
            int cantidad = carritoProducto.getCantidad();
            
            if (!VerificarStock(producto, cantidad)) {
                throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombreProducto());
            }
            
            totalPedido += carritoProducto.getPrecioTotal();
        }

        // Crear el pedido utilizando la función existente
        Pedido pedido = pedidoService.crearPedido(idUsuario, totalPedido);

        // Agregar productos al pedido y actualizar el stock utilizando la función existente
        for (CarritoProducto carritoProducto : productos) {
            Producto producto = carritoProducto.getProducto();
            int cantidad = carritoProducto.getCantidad();
            
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
            
            pedidoProductoService.AgregarProducto(pedido.getIdPedido(), producto.getProductoId(), cantidad);
        }
        
        // Eliminar los productos del carrito
        carritoProductoRepository.deleteAll(productos);
    }


  
    @Override
    public CarritoDto findByCarrito(Long idUsuario) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito);
        
        List<CarritoProductoDto> productosDTO = productos.stream()
                .map(producto -> {
                    CarritoProductoDto dto = new CarritoProductoDto();
                    dto.setIdCarritoProducto(producto.getIdCarritoProducto());
                    dto.setProducto(producto.getProducto());
                    dto.setCantidad(producto.getCantidad());
                    dto.setPrecioTotal(producto.getPrecioTotal());
                    return dto;
                })
                .toList();

        CarritoDto response = new CarritoDto();
        response.setIdCarrito(carrito.getIdCarrito());
        response.setProductos(productosDTO);

        return response;
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
    
}

