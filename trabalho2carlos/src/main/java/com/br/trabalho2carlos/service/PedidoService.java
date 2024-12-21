package com.br.trabalho2carlos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.trabalho2carlos.model.Cliente;
import com.br.trabalho2carlos.model.DetalhesPedido;
import com.br.trabalho2carlos.model.Pedido;
import com.br.trabalho2carlos.model.Produto;
import com.br.trabalho2carlos.model.dto.PedidoDTO;
import com.br.trabalho2carlos.model.dto.DetalhesPedidoDTO;
import com.br.trabalho2carlos.repository.ClienteRepository;
import com.br.trabalho2carlos.repository.PedidoRepository;
import com.br.trabalho2carlos.repository.ProdutoRepository;
import com.br.trabalho2carlos.utils.PedidoMapper;
import com.br.trabalho2carlos.utils.DetalhesPedidoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoMapper pedidoMapper;
    private final DetalhesPedidoMapper detalhesPedidoMapper;

    public PedidoDTO criarPedido(PedidoDTO pedidoDTO, String mode) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setCliente(cliente);

        List<DetalhesPedido> detalhes = pedidoDTO.getDetalhes().stream().map(d -> {
            DetalhesPedido detalhe = detalhesPedidoMapper.toEntity(d);

            // Se o modo for pessimist, usar findByIdForUpdate, caso contrário findById
            // normal.
            Optional<Produto> produtoOpt;
            if ("pessimistic".equalsIgnoreCase(mode)) {
                produtoOpt = produtoRepository.findByIdForUpdate(d.getProdutoId());
            } else {
                produtoOpt = produtoRepository.findById(d.getProdutoId());
            }

            Produto produto = produtoOpt
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (produto.getProdutoUnidadesEmEstoque() < d.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getProdutoNome());
            }

            produto.setProdutoUnidadesEmEstoque(produto.getProdutoUnidadesEmEstoque() - d.getQuantidade());

            detalhe.setProduto(produto);
            detalhe.setPedido(pedido);

            return detalhe;
        }).collect(Collectors.toList());

        pedido.setDetalhes(detalhes);
        Pedido salvo = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(salvo);
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PedidoDTO> buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDTO);
    }

    public PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO, String mode) {
        return pedidoRepository.findById(id).map(pedido -> {
            Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            pedido.setCliente(cliente);

            // Restaurar estoque do pedido antigo
            for (DetalhesPedido detalheAntigo : pedido.getDetalhes()) {
                Produto produtoAntigo = detalheAntigo.getProduto();
                produtoAntigo.setProdutoUnidadesEmEstoque(
                        produtoAntigo.getProdutoUnidadesEmEstoque() + detalheAntigo.getQuantidade());
            }

            pedido.getDetalhes().clear();

            List<DetalhesPedido> novosDetalhes = pedidoDTO.getDetalhes().stream().map(d -> {
                DetalhesPedido detalhe = detalhesPedidoMapper.toEntity(d);

                Optional<Produto> produtoOpt;
                if ("pessimistic".equalsIgnoreCase(mode)) {
                    produtoOpt = produtoRepository.findByIdForUpdate(d.getProdutoId());
                } else {
                    produtoOpt = produtoRepository.findById(d.getProdutoId());
                }

                Produto produto = produtoOpt
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                if (produto.getProdutoUnidadesEmEstoque() < d.getQuantidade()) {
                    throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getProdutoNome());
                }

                produto.setProdutoUnidadesEmEstoque(produto.getProdutoUnidadesEmEstoque() - d.getQuantidade());

                detalhe.setProduto(produto);
                detalhe.setPedido(pedido);
                return detalhe;
            }).collect(Collectors.toList());

            pedido.getDetalhes().addAll(novosDetalhes);

            Pedido atualizado = pedidoRepository.save(pedido);
            return pedidoMapper.toDTO(atualizado);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }

    public void deletarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pedido não encontrado.");
        }
    }
}
