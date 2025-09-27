package com.projeto.dsc.jwt;

import com.projeto.dsc.model.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDatails extends User {

    private final Usuario usuario;

    public JwtUserDatails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    public Long getId() {
        return this.usuario.getId();
    }

    public String getRole() {
        return this.usuario.getRole().name();
    }
}
