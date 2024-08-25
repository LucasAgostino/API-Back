package com.api.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Pedido;
import com.api.api.dominio.PedidoProducto;
import com.api.api.dominio.Producto;
import com.api.api.repository.PedidoProductoRepository;
import com.api.api.repository.PedidoRepository;
import com.api.api.repository.ProductoRepository;
import com.api.api.service.Interfaces.PedidoProductoService;

@Service
public class PedidoProductoServiceImpl implements PedidoProductoService {

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public void AgregarProducto(Long idPedido, Long idProducto, int cantidad) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        Pedido pedido = pedidoOpt.get();
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        Producto producto = productoOpt.get();
        
        PedidoProducto pedidoProducto = new PedidoProducto();
        pedidoProducto.setCantidad(cantidad);
        pedidoProducto.setPedido(pedido);
        pedidoProducto.setProducto(producto);
        pedidoProducto.setPrecioTotal(producto.getPrecio()*cantidad);
        pedidoProductoRepository.save(pedidoProducto);
    }
    
}
