package com.api.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api.dominio.Pedido;
import com.api.api.dominio.Usuario;
import com.api.api.repository.PedidoRepository;
import com.api.api.repository.UsuarioRepository;
import com.api.api.service.Interfaces.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Pedido crearPedido(Long idUsuario, float total) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Usuario usuario = usuarioOpt.get();
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotalPedido(total);
        return pedidoRepository.save(pedido);
    }
}
