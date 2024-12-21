package com.br.trabalho2carlos.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoID;

    private String produtoNome;
    private String produtoCategoria;
    private Double produtoPreco;
    private Integer produtoUnidadesEmEstoque;
    private String produtoImagem;

    @Version
    private Long version;

}
