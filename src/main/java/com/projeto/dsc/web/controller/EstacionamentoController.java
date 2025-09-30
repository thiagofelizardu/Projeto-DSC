package com.projeto.dsc.web.controller;


import com.projeto.dsc.model.entity.ClienteVaga;
import com.projeto.dsc.model.service.ClienteVagaService;
import com.projeto.dsc.model.service.EstacionamentoService;
import com.projeto.dsc.web.dto.EstacionamentoCreateDto;
import com.projeto.dsc.web.dto.EstacionamentoResponseDto;
import com.projeto.dsc.web.dto.mapper.ClienteMapper;
import com.projeto.dsc.web.dto.mapper.ClienteVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    private final ClienteVagaService clienteVagaService;

    @PostMapping("/check-in")
    public ResponseEntity<EstacionamentoResponseDto> checkin(@RequestBody @Valid EstacionamentoCreateDto estacionamentoCreateDto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(estacionamentoCreateDto);
        estacionamentoService.checkIn(clienteVaga);
        EstacionamentoResponseDto estacionamentoResponseDto = ClienteVagaMapper.toDto(clienteVaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(estacionamentoResponseDto);
    }

    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponseDto estacionamentoResponseDto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.ok(estacionamentoResponseDto);
    }

    @GetMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkout(@PathVariable String recibo) {
        ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);
        EstacionamentoResponseDto estacionamentoResponseDto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.ok(estacionamentoResponseDto);
    }
}
