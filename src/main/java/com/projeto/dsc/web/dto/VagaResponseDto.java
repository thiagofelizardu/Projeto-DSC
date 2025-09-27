package com.projeto.dsc.web.dto;

import com.projeto.dsc.model.enums.StatusVaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaResponseDto {

    private Long id;
    @NotBlank
    @Size(min = 4, max = 4)
    private String codigo;

    @Pattern(regexp = "LIVRE|OCUPADDA")
    private StatusVaga status;
}
