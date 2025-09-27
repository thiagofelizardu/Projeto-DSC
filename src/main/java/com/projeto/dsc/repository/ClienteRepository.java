package com.projeto.dsc.repository;

import com.projeto.dsc.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsuarioId(Long id);
}
