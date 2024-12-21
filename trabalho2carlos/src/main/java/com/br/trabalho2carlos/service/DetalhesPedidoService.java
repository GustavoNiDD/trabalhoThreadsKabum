package com.br.trabalho2carlos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.trabalho2carlos.model.DetalhesPedido;
import com.br.trabalho2carlos.model.Produto;
import com.br.trabalho2carlos.model.Pedido;
import com.br.trabalho2carlos.model.dto.DetalhesPedidoDTO;
import com.br.trabalho2carlos.repository.DetalhesPedidoRepository;
import com.br.trabalho2carlos.repository.PedidoRepository;
import com.br.trabalho2carlos.repository.ProdutoRepository;
import com.br.trabalho2carlos.utils.DetalhesPedidoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DetalhesPedidoService {

    private final DetalhesPedidoRepository detalhesPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final DetalhesPedidoMapper detalhesPedidoMapper;

    public DetalhesPedidoDTO criarDetalhe(DetalhesPedidoDTO dto) {
        DetalhesPedido detalhe = detalhesPedidoMapper.toEntity(dto);
        // Atribuir pedido e produto
        Pedido pedido = pedidoRepository.findById(detalhe.getPedido().getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        detalhe.setPedido(pedido);
        detalhe.setProduto(produto);
        DetalhesPedido salvo = detalhesPedidoRepository.save(detalhe);
        return detalhesPedidoMapper.toDTO(salvo);
    }

    public List<DetalhesPedidoDTO> listarTodos() {
        return detalhesPedidoRepository.findAll().stream()
                .map(detalhesPedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<DetalhesPedidoDTO> buscarPorId(Long id) {
        return detalhesPedidoRepository.findById(id)
                .map(detalhesPedidoMapper::toDTO);
    }

    public DetalhesPedidoDTO atualizarDetalhe(Long id, DetalhesPedidoDTO dto) {
        return detalhesPedidoRepository.findById(id)
                .map(detalhe -> {
                    Produto produto = produtoRepository.findById(dto.getProdutoId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
                    detalhe.setProduto(produto);
                    detalhe.setPrecoVenda(dto.getPrecoVenda());
                    detalhe.setQuantidade(dto.getQuantidade());
                    detalhe.setDesconto(dto.getDesconto());
                    DetalhesPedido atualizado = detalhesPedidoRepository.save(detalhe);
                    return detalhesPedidoMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RuntimeException("Detalhe não encontrado."));
    }

    public void deletarDetalhe(Long id) {
        if (detalhesPedidoRepository.existsById(id)) {
            detalhesPedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Detalhe não encontrado.");
        }
    }
}
