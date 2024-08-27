package com.api.api.service.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.api.DTO.PedidoDto;
import com.api.api.DTO.PedidoProductoDto;
import com.api.api.dominio.Pedido;
import com.api.api.dominio.PedidoProducto;
import com.api.api.repository.PedidoRepository;

@Service
public class PedidoDao {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDto> findAll(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
public Optional<PedidoDto> findById(Long id) {
    Optional<Pedido> pedido = pedidoRepository.findById(id);
    return pedido.map(this::convertToDto);
}

    @Transactional(readOnly = true)
    public List<PedidoDto> findByUsuarioId(Long idUsuario){
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(idUsuario);
        return pedidos.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    


    private PedidoDto convertToDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setIdUsuario(pedido.getUsuario().getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setTotalPedido(pedido.getTotalPedido());
        dto.setPedidoProductos(
            pedido.getPedidoProductos().stream()
                  .map(this::convertToDto)
                  .collect(Collectors.toSet())
        );
        return dto;
    }

    private PedidoProductoDto convertToDto(PedidoProducto pedidoProducto) {
        PedidoProductoDto dto = new PedidoProductoDto();
        dto.setIdPedidoProducto(pedidoProducto.getIdPedidoProducto());
        dto.setIdPedido(pedidoProducto.getPedido().getIdPedido());
        dto.setIdProducto(pedidoProducto.getProducto().getProductoId());
        dto.setCantidad(pedidoProducto.getCantidad());
        dto.setPrecioTotal(pedidoProducto.getPrecioTotal());
        return dto;
    }
}
