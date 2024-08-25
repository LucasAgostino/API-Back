package com.api.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    
    @Override
    public List<CarritoProducto> findByCarrito(Long idUsuario){
        Optional<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito2 = carrito.get();
        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito2);
        return productos;
    }

    @Override
    public String eliminarProducto(Long idProducto, Long idUsuario) {
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (!productoOpt.isPresent()) {
            return "Producto no encontrado";
        }
        
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        Carrito carrito = carritoOpt.get();
        
        Optional<CarritoProducto> carritoproductoOpt = carritoProductoRepository.findByCarritoIdAndProductoId(carrito.getIdCarrito(), idProducto);
        if (carritoproductoOpt.isPresent()) {
            CarritoProducto carritoProducto = carritoproductoOpt.get();
            carritoProductoRepository.delete(carritoProducto);
            return "Producto eliminado del carrito";
        }
        return "Producto no encontrado en el carrito";
    }

    @Override
    public String restarCarrito(Long idUsuario, Long idProducto, int cantidad) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        
        Carrito carrito = carritoOpt.get();
        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito);
        
        CarritoProducto productoEncontrado = null;
        for (CarritoProducto producto : productos) {
            if (producto.getProducto().getProductoId().equals(idProducto)) {
                productoEncontrado = producto;
                break;
            }
        }
        
        if (productoEncontrado == null) {
            return "Producto no encontrado en el carrito.";
        }
        Producto producto = getProducto(idProducto);
        int nuevaCantidad = productoEncontrado.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            carritoProductoRepository.delete(productoEncontrado);
        } else {
            int stockViejo = productoEncontrado.getCantidad();
            productoEncontrado.setCantidad(nuevaCantidad);
            productoEncontrado.setPrecioTotal(producto.getPrecio() * (stockViejo - cantidad));
            carritoProductoRepository.save(productoEncontrado);
            
        }
        
        return "Producto restado del carrito exitosamente.";
    }
    
    public void realizarPedido(Long idUsuario) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(idUsuario);
        
        if (carritoOpt.isEmpty()) {
            throw new RuntimeException("Carrito no encontrado para el usuario con ID: " + idUsuario);
        }
        
        Carrito carrito = carritoOpt.get();
        List<CarritoProducto> productos = carritoProductoRepository.findByCarrito(carrito);
        
        if (productos.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede realizar un pedido sin productos.");
        }
    
        float totalPedido = 0;
    
        // Verificar stock de todos los productos del carrito
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
            
            // Update the product stock
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
            pedidoProductoService.AgregarProducto(pedido.getIdPedido(), producto.getProductoId(), cantidad);
        }             
        carritoProductoRepository.deleteAll(productos);
        vaciarCarrito(carrito);    
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
        carrito.getCarritoProductos().clear();
        carritoRepository.save(carrito);
    }
}

