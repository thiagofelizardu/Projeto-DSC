package com.projeto.dsc.web.controller;


import com.projeto.dsc.jwt.JwtUserDatails;
import com.projeto.dsc.model.entity.Cliente;
import com.projeto.dsc.model.service.ClienteService;
import com.projeto.dsc.model.service.UsuarioService;
import com.projeto.dsc.web.dto.ClienteCreateDto;
import com.projeto.dsc.web.dto.ClienteResponseDto;
import com.projeto.dsc.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;


    @PostMapping()
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto, @AuthenticationPrincipal JwtUserDatails userDetails) {

        Cliente cliente = ClienteMapper.toClient(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));

        Cliente saved = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDto(saved));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@RequestParam long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getAll(Pageable pageable) {
        Page<Cliente> cliente = clienteService.buscartodos(pageable);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDatails userDetails) {
        Cliente cliente = clienteService.buscarPorUsuarioId(userDetails.getId());
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }



}
