package com.projeto.dsc.model.service;

import com.projeto.dsc.model.entity.Usuario;
import com.projeto.dsc.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioServiceImpl {

    Usuario salvar(Usuario usuario);

    Usuario buscarPorId(Long id);

    Usuario editarSenha(Long id, String password, String novaSenha, String confirmaSenha);

    Page<Usuario> buscarTodosUsuarios(Pageable pageable);

    Role buscarRolePorUserName(String username);

    Usuario buscarPorUserName(String username);
}
