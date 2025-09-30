package com.projeto.dsc.model.service;


import com.projeto.dsc.model.entity.ClienteVaga;
import com.projeto.dsc.repository.ClienteVaagaRepository;
import com.projeto.dsc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVaagaRepository clienteVaagaRepository;


    @Transactional
    public ClienteVaga save(ClienteVaga clienteVaga){
        return clienteVaagaRepository.save(clienteVaga);
    }


    public ClienteVaga buscarPorRecibo(String recibo) {
        return clienteVaagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(()-> new EntityNotFoundException(String.format("Recibo %s não encontrado no sistema ou ja tem o checkout", recibo)));
    }
}
