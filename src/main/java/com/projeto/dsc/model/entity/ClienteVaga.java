package com.projeto.dsc.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "clientes_tem_vagas")
public class ClienteVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recibo",nullable = false, unique = true, length = 15)
    private String recibo;

    @Column(name = "placa",nullable = false,  length = 8)
    private String placa;

    @Column(name = "marca",nullable = false,  length = 45)
    private String marca;

    @Column(name = "modelo",nullable = false,  length = 45)
    private String modelo;

    @Column(name = "cor",nullable = false, length = 15)
    private String cor;

    @Column(name = "data_entrada",nullable = false, length = 15)
    private Date dataEntrada;

    @Column(name = "data_saida")
    private Date dataSaida;

    @Column(name = "valor",columnDefinition = "decimal(7,2)")
    private BigDecimal valor;

    @Column(name = "desconto",columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vaga",nullable = false)
    private Vaga Vaga;


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
