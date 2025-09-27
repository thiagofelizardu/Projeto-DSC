package com.projeto.dsc.model.service;

import com.projeto.dsc.model.entity.Cliente;
import com.projeto.dsc.repository.ClienteRepository;
import com.projeto.dsc.web.exception.CpfUniqueViolationException;
import com.projeto.dsc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF JA EXISTENTE NO SISTEMA: %s", cliente.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("Clinte id='%s' nao encontrado no sistema", id)));
    }


    @Transactional(readOnly = true)
    public Page<Cliente> buscartodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }
}
