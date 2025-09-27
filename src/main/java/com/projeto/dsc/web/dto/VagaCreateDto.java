package com.projeto.dsc.web.dto;

import com.projeto.dsc.model.enums.StatusVaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaCreateDto {

    @NotBlank
    @Size(min = 4, max = 4)
    private String codigo;

    private StatusVaga status;
}
