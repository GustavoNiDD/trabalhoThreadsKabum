package com.br.trabalho2carlos.utils;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.br.trabalho2carlos.model.Produto;
import com.br.trabalho2carlos.model.dto.ProdutoDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoDTO toDTO(Produto produto);

    Produto toEntity(ProdutoDTO produtoDTO);
}
