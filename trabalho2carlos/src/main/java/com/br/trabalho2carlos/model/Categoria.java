package com.br.trabalho2carlos.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaID;

    private String categoriaNome;
    private String categoriaDescricao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categoria_produto", joinColumns = @JoinColumn(name = "categoria_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private Set<Produto> produtos = new HashSet<>();
}
