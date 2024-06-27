package com.motorepuestos.melos.service;
import com.motorepuestos.melos.data.model.CitaDTO;

import java.util.List;

public interface CitaService {
    CitaDTO createCita(CitaDTO citaDTO);
    CitaDTO updateCita(Long id, CitaDTO citaDTO);
    void deleteCita(Long id);
    CitaDTO getCitaById(Long id);
    List<CitaDTO> getAllCitas();
}