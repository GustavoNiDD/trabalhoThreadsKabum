package com.br.trabalho2carlos.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DetalhesPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalhePedidoId;

    // Muitos detalhes para um pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Cada detalhe se refere a um produto específico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Double precoVenda;
    private Short quantidade;
    private Double desconto;
}
