package com.br.trabalho2carlos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.trabalho2carlos.model.Produto;
import com.br.trabalho2carlos.model.dto.ProdutoDTO;
import com.br.trabalho2carlos.repository.ProdutoRepository;
import com.br.trabalho2carlos.utils.ProdutoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.toEntity(produtoDTO);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(salvo);
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toDTO);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id)
                .map(prod -> {
                    prod.setProdutoNome(produtoDTO.getProdutoNome());
                    prod.setProdutoCategoria(produtoDTO.getProdutoCategoria());
                    prod.setProdutoPreco(produtoDTO.getProdutoPreco());
                    prod.setProdutoUnidadesEmEstoque(produtoDTO.getProdutoUnidadesEmEstoque());
                    prod.setProdutoImagem(produtoDTO.getProdutoImagem());
                    Produto atualizado = produtoRepository.save(prod);
                    return produtoMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    public void deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado.");
        }
    }
}
