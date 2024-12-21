package com.br.trabalho2carlos.utils;

import org.mapstruct.Mapper;
import com.br.trabalho2carlos.model.DetalhesPedido;
import com.br.trabalho2carlos.model.dto.DetalhesPedidoDTO;

@Mapper(componentModel = "spring")
public interface DetalhesPedidoMapper {
    DetalhesPedidoDTO toDTO(DetalhesPedido detalhesPedido);

    DetalhesPedido toEntity(DetalhesPedidoDTO detalhesPedidoDTO);
}
