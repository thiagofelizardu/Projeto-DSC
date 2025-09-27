package com.projeto.dsc.model.entity;

import com.projeto.dsc.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "db_usuarios")
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username",nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password",nullable = false,length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    @Column(name = "data_Criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_Modificacao")
    private LocalDateTime dataModificacao;

    @Column(name = "criado_Por")
    private String criadoPor;

    @Column(name = "modificado_Por")
    private String modificadoPor;

}
