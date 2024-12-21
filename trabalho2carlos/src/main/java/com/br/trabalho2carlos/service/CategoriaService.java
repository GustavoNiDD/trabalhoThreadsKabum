package com.br.trabalho2carlos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.trabalho2carlos.model.Categoria;
import com.br.trabalho2carlos.model.dto.CategoriaDTO;
import com.br.trabalho2carlos.repository.CategoriaRepository;
import com.br.trabalho2carlos.utils.CategoriaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria salvo = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(salvo);
    }

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoriaDTO> buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO);
    }

    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .map(cat -> {
                    cat.setCategoriaNome(categoriaDTO.getCategoriaNome());
                    cat.setCategoriaDescricao(categoriaDTO.getCategoriaDescricao());
                    Categoria atualizado = categoriaRepository.save(cat);
                    return categoriaMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }

    public void deletarCategoria(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Categoria não encontrada.");
        }
    }
}
