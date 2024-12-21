package com.br.trabalho2carlos.model.dto;

import lombok.Data;

@Data
public class DetalhesPedidoDTO {
    private Long detalhePedidoId;
    private Long produtoId;
    private Double precoVenda;
    private Short quantidade;
    private Double desconto;
}
