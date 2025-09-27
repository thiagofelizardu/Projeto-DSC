package com.projeto.dsc.web.dto.mapper;


import com.projeto.dsc.model.entity.Vaga;
import com.projeto.dsc.web.dto.VagaResponseDto;
import com.projeto.dsc.web.dto.VagaCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {


    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
