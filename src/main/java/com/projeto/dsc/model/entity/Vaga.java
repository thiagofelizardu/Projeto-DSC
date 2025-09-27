package com.projeto.dsc.model.entity;


import com.projeto.dsc.model.enums.StatusVaga;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_vaga")
public class Vaga implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo",nullable = false, unique = true, length = 4)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private StatusVaga status;

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
