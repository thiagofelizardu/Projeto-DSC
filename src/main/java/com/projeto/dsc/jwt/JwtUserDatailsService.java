package com.projeto.dsc.jwt;

import com.projeto.dsc.model.entity.Usuario;
import com.projeto.dsc.model.enums.Role;
import com.projeto.dsc.model.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDatailsService implements UserDetailsService {

    private final UsuarioService  usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUserName(username);
        return new  JwtUserDatails(usuario);
    }

    public JwtToken getTokenAuthnticated(String username) {
       Role role = usuarioService.buscarRolePorUserName(username);
        return JwtUtls.createToken(username,role.name().substring("ROLE_".length()));
    }
}
