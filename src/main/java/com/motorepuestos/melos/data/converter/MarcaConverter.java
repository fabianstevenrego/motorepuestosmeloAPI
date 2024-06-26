package com.motorepuestos.melos.data.converter;


import com.motorepuestos.melos.data.entity.Marca;
import com.motorepuestos.melos.data.model.MarcaDTO;
import org.springframework.stereotype.Component;

@Component
public class MarcaConverter {

    public MarcaDTO entityToDto(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(marca.getId());
        dto.setNombre(marca.getNombre());
        return dto;
    }

    public Marca dtoToEntity(MarcaDTO dto) {
        Marca marca = new Marca();
        marca.setId(dto.getId());
        marca.setNombre(dto.getNombre());
        return marca;
    }
}

