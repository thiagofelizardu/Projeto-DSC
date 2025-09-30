package com.projeto.dsc.repository;

import com.projeto.dsc.model.entity.Vaga;
import com.projeto.dsc.model.enums.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(StatusVaga statusVaga);
}
