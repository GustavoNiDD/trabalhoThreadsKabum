package com.br.trabalho2carlos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.trabalho2carlos.model.dto.PedidoDTO;
import com.br.trabalho2carlos.service.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(
            @RequestBody PedidoDTO pedidoDTO,
            @RequestParam(defaultValue = "noLock") String mode) {
        return ResponseEntity.ok(pedidoService.criarPedido(pedidoDTO, mode));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        Optional<PedidoDTO> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO pedidoDTO,
            @RequestParam(defaultValue = "noLock") String mode) {
        try {
            return ResponseEntity.ok(pedidoService.atualizarPedido(id, pedidoDTO, mode));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
