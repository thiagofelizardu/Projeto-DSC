package com.projeto.dsc.repository;

import com.projeto.dsc.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsuarioId(Long id);

    Optional<Cliente> findByCpf(String cpf);
}
