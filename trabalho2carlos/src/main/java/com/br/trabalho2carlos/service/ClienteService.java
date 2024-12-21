package com.br.trabalho2carlos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.trabalho2carlos.model.Cliente;
import com.br.trabalho2carlos.model.dto.ClienteDTO;
import com.br.trabalho2carlos.repository.ClienteRepository;
import com.br.trabalho2carlos.utils.ClienteMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toDTO(salvo);
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteDTO.getNome());
                    cliente.setCargo(clienteDTO.getCargo());
                    cliente.setEndereco(clienteDTO.getEndereco());
                    cliente.setCidade(clienteDTO.getCidade());
                    cliente.setCep(clienteDTO.getCep());
                    cliente.setPais(clienteDTO.getPais());
                    cliente.setTelefone(clienteDTO.getTelefone());
                    cliente.setFax(clienteDTO.getFax());
                    Cliente atualizado = clienteRepository.save(cliente);
                    return clienteMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    public void deletarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente não encontrado.");
        }
    }
}
