package com.motorepuestos.melos.data.converter;


import com.motorepuestos.melos.data.entity.Tipo;
import com.motorepuestos.melos.data.model.TipoDTO;
import org.springframework.stereotype.Component;

@Component
public class TipoConverter {

    public TipoDTO entityToDto(Tipo tipo) {
        TipoDTO dto = new TipoDTO();
        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());
        return dto;
    }

    public Tipo dtoToEntity(TipoDTO dto) {
        Tipo tipo = new Tipo();
        tipo.setId(dto.getId());
        tipo.setNombre(dto.getNombre());
        return tipo;
    }
}
