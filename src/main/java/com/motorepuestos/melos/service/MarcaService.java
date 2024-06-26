package com.motorepuestos.melos.service;



import com.motorepuestos.melos.data.entity.Marca;
import com.motorepuestos.melos.data.model.MarcaDTO;

import java.util.List;

public interface MarcaService {
    List<MarcaDTO> getAllMarcas();
    MarcaDTO getMarcaById(Long id);
    MarcaDTO createMarca(MarcaDTO marcaDTO);
    MarcaDTO updateMarca(Long id, MarcaDTO marcaDTO);
    void deleteMarca(Long id);
    Marca findById(Long id);

}
