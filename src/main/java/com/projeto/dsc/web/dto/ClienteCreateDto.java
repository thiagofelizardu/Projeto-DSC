package com.projeto.dsc.web.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreateDto {

    @NonNull
    @Size(min = 5, max =100)
    private String nome;

    @CPF
    @Size(min = 11, max =11)
    private String cpf;
}
