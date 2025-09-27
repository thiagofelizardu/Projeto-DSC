package com.projeto.dsc.web.controller;

import com.projeto.dsc.model.entity.Usuario;
import com.projeto.dsc.model.service.UsuarioService;
import com.projeto.dsc.web.dto.UsuarioCreateDto;
import com.projeto.dsc.web.dto.UsuarioSenhaDto;
import com.projeto.dsc.web.dto.mapper.UsuarioMapper;
import com.projeto.dsc.web.dto.UsuarioResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/createUser")
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario novoUsuario = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(novoUsuario));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario userId = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(userId));
    }
    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        usuarioService.editarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsuarioResponseDto>> getAllUser(Pageable usuario) {
        Page<Usuario> userId = usuarioService.buscarTodosUsuarios(usuario);
        return ResponseEntity.ok(UsuarioMapper.toPageDto(userId));
    }

}
