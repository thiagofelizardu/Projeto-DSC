package com.projeto.dsc.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstacionamentoResponseDto {

    private String placa;

    private String marca;

    private String modelo;

    private String cor;

    private String clienteCpf;

    private String recibo;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dataEntrada;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dataSaida;
    private String vagaCodigo;

    private BigDecimal valor;
    private BigDecimal desconto;
}
