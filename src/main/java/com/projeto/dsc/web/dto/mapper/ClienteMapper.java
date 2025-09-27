package com.projeto.dsc.web.dto.mapper;

import com.projeto.dsc.model.entity.Cliente;
import com.projeto.dsc.web.dto.ClienteCreateDto;
import com.projeto.dsc.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toClient(ClienteCreateDto dto) {
        return new ModelMapper().map(dto, Cliente.class);
    }


    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
