package com.br.trabalho2carlos.utils;

import org.mapstruct.Mapper;
import com.br.trabalho2carlos.model.Cliente;
import com.br.trabalho2carlos.model.dto.ClienteDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteDTO clienteDTO);
}
