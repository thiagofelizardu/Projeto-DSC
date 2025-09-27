package com.projeto.dsc.web.controller;


import com.projeto.dsc.jwt.JwtToken;
import com.projeto.dsc.jwt.JwtUserDatailsService;
import com.projeto.dsc.web.dto.UsuarioLoginDto;
import com.projeto.dsc.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticacaoController {


    private final JwtUserDatailsService jwtUserDatailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto, HttpServletRequest request) {
        log.info("Iniciando autenticacao com o login {}", usuarioLoginDto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioLoginDto.getUsername(), usuarioLoginDto.getPassword());

            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDatailsService.getTokenAuthnticated(usuarioLoginDto.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
          log.error("Erro ao autenticar o login {}", usuarioLoginDto.getUsername());
        }
        return ResponseEntity
                .badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "creedenciais invalidas"));
    }


}
