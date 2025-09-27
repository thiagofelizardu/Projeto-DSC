package com.projeto.dsc.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome",nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true,length = 11)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @CreatedDate
    @Column(name = "data_Criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_Modificacao")
    private LocalDateTime dataModificacao;

    @CreatedBy
    @Column(name = "criado_Por")
    private String criadoPor;

    @LastModifiedDate
    @Column(name = "modificado_Por")
    private String modificadoPor;


}
