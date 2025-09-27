package com.projeto.dsc.model.service;

import com.projeto.dsc.model.entity.Usuario;
import com.projeto.dsc.model.enums.Role;
import com.projeto.dsc.repository.UsuarioRepository;
import com.projeto.dsc.web.exception.EntityNotFoundException;
import com.projeto.dsc.web.exception.UserNameUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService implements UsuarioServiceImpl {


    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);

        }catch (DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("User {%s} já caadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario id=%s nao encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("Nova Senha não confere com o confirmação da senha");
        }
        Usuario usuario = buscarPorId(id);
        if(!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new RuntimeException("Sua senha não confere");
        }

        usuario.setPassword(passwordEncoder.encode(senhaAtual));
        return usuario;
    }


    @Transactional(readOnly = true)
    public Page<Usuario> buscarTodosUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUserName(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario com username=%s nao encontrado", username))
        );
    }

    public Role buscarRolePorUserName(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }


}
