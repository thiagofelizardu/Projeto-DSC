package com.projeto.dsc.repository;

import com.projeto.dsc.model.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteVaagaRepository extends JpaRepository<ClienteVaga,Long> {
    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);
}
