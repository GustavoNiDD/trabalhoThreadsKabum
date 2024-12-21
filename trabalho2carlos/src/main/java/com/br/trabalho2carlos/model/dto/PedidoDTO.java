package com.br.trabalho2carlos.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long pedidoId;
    private Long clienteId;
    private LocalDateTime dataPedido;
    private List<DetalhesPedidoDTO> detalhes;
}
