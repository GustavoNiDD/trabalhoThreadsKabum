package com.br.trabalho2carlos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.trabalho2carlos.model.dto.DetalhesPedidoDTO;
import com.br.trabalho2carlos.service.DetalhesPedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/detalhes-pedidos")
@RequiredArgsConstructor
public class DetalhesPedidoController {

    private final DetalhesPedidoService detalhesPedidoService;

    @PostMapping
    public ResponseEntity<DetalhesPedidoDTO> criarDetalhe(@RequestBody DetalhesPedidoDTO dto) {
        return ResponseEntity.ok(detalhesPedidoService.criarDetalhe(dto));
    }

    @GetMapping
    public ResponseEntity<List<DetalhesPedidoDTO>> listarTodos() {
        return ResponseEntity.ok(detalhesPedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesPedidoDTO> buscarPorId(@PathVariable Long id) {
        Optional<DetalhesPedidoDTO> detalhe = detalhesPedidoService.buscarPorId(id);
        return detalhe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesPedidoDTO> atualizarDetalhe(@PathVariable Long id,
            @RequestBody DetalhesPedidoDTO dto) {
        try {
            return ResponseEntity.ok(detalhesPedidoService.atualizarDetalhe(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDetalhe(@PathVariable Long id) {
        try {
            detalhesPedidoService.deletarDetalhe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
