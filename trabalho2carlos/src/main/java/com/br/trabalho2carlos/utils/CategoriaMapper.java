package com.br.trabalho2carlos.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.br.trabalho2carlos.model.Categoria;
import com.br.trabalho2carlos.model.dto.CategoriaDTO;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // Mapeia CategoriaDTO para Categoria
    Categoria toEntity(CategoriaDTO categoriaDTO);

    // Mapeia Categoria para CategoriaDTO
    CategoriaDTO toDTO(Categoria categoria);

    // Mapeia um Set<CategoriaDTO> para Set<Categoria>
    default Set<Categoria> toEntitySet(Set<CategoriaDTO> categoriaDTOs) {
        if (categoriaDTOs == null || categoriaDTOs.isEmpty()) {
            return new HashSet<>();
        }
        return categoriaDTOs.stream()
                .map(this::toEntity) // Converte cada CategoriaDTO para Categoria
                .collect(Collectors.toSet());
    }
}
