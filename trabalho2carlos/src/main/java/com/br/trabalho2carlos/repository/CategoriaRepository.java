package com.br.trabalho2carlos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.trabalho2carlos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
