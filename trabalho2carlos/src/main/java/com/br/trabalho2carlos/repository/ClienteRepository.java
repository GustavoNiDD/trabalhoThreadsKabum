package com.br.trabalho2carlos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.trabalho2carlos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
