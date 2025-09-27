package com.projeto.dsc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDto {

    @NotBlank
    @Size(min = 6, max = 10,message = "a senha deve conter no maximo 10 caracteres")
    private String senhaAtual;
    @NotBlank
    @Size(min = 6, max = 10,message = "a senha deve conter no maximo 10 caracteres")
    private String novaSenha;
    @NotBlank
    @Size(min = 6, max = 10,message = "a senha deve conter no maximo 10 caracteres")
    private String confirmaSenha;

}
