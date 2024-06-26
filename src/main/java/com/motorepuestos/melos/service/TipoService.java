package com.motorepuestos.melos.service;

import com.motorepuestos.melos.data.entity.Tipo;
import com.motorepuestos.melos.data.model.MarcaDTO;
import com.motorepuestos.melos.data.model.TipoDTO;

import java.util.List;

public interface TipoService {
    List<TipoDTO> getAllTipos();
    TipoDTO getTipoById(Long id);
    TipoDTO createTipo(TipoDTO tipoDTO);
    TipoDTO updateTipo(Long id, TipoDTO tipoDTO);
    void deleteTipo(Long id);
    Tipo findById(Long id);

}
