package com.projeto.dsc.model.service;

import com.projeto.dsc.model.entity.Vaga;
import com.projeto.dsc.repository.VagaRepository;
import com.projeto.dsc.web.exception.CodigoUniqueViolationException;
import com.projeto.dsc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.projeto.dsc.model.enums.StatusVaga.LIVRE;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga save(Vaga vaga){
        try {
            return vagaRepository.save(vaga);
        }catch (DataIntegrityViolationException e){
            throw new CodigoUniqueViolationException(String.format("vago com o codigo = %s ja cadastrada", vaga.getCodigo()));
        }
    }
    @Transactional(readOnly = true)
    public Vaga findByCodigo(String codigo){
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Vaga com o codigo = '%s' ja cadastrada",codigo))
        );
    }
    @Transactional(readOnly = true)
    public Vaga buscarVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(()-> new EntityNotFoundException("Nenhuma vaga encontrada"));
    }
}
