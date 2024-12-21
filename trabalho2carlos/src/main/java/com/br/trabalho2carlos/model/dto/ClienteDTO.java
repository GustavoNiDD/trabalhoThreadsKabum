package com.br.trabalho2carlos.model.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long clienteID;
    private String nome;
    private String cargo;
    private String endereco;
    private String cidade;
    private String cep;
    private String pais;
    private String telefone;
    private String fax;
}
