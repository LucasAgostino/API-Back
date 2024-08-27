package com.api.api.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.api.api.DTO.PedidoDto;
import com.api.api.DTO.PedidoProductoDto;
import com.api.api.dominio.Pedido;
import com.api.api.dominio.PedidoProducto;
import com.api.api.repository.PedidoProductoRepository;
import com.api.api.repository.PedidoRepository;

@Service
public class PedidoDao {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

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
        dto.setNombreUsuario(pedido.getUsuario().getNombreUsuario());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setTotalPedido(pedido.getTotalPedido());

        // Fetch and set PedidoProducto details
        List<PedidoProducto> pedidoProductos = pedidoProductoRepository.findByPedido_IdPedido(pedido.getIdPedido());
        Set<PedidoProductoDto> pedidoProductoDtos = pedidoProductos.stream()
                .map(this::convertToPedidoProductoDto)
                .collect(Collectors.toSet());
        dto.setPedidoProductos(pedidoProductoDtos);

        return dto;
    }

    private PedidoProductoDto convertToPedidoProductoDto(PedidoProducto pedidoProducto) {
        PedidoProductoDto pedidoProductoDto = new PedidoProductoDto();
        pedidoProductoDto.setIdPedido(pedidoProducto.getPedido().getIdPedido());
        pedidoProductoDto.setNombreProducto(pedidoProducto.getProducto().getNombreProducto());
        pedidoProductoDto.setCantidad(pedidoProducto.getCantidad());
        pedidoProductoDto.setPrecioTotal(pedidoProducto.getPrecioTotal());
        return pedidoProductoDto;
    }
}
