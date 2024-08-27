package com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;

import com.api.api.DTO.PedidoDto;
import com.api.api.service.Interfaces.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/get")
    public List<PedidoDto> obtenerPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PedidoDto> obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @GetMapping("/get/byusuario/{idUsuario}")
    public List<PedidoDto> obtenerPedidosPorUsuario(@PathVariable Long idUsuario) {
        return pedidoService.findByUsuarioId(idUsuario);
    }
}
