package com.br.trabalho2carlos.model.dto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private Long produtoID;
    private String produtoNome;
    private String produtoCategoria;
    private Double produtoPreco;
    private Integer produtoUnidadesEmEstoque;
    private String produtoImagem;
}
