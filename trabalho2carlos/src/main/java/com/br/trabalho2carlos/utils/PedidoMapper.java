package com.br.trabalho2carlos.utils;

import org.mapstruct.Mapper;
import com.br.trabalho2carlos.model.Pedido;
import com.br.trabalho2carlos.model.dto.PedidoDTO;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    PedidoDTO toDTO(Pedido pedido);

    Pedido toEntity(PedidoDTO pedidoDTO);
}
