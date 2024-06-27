package com.motorepuestos.melos.service.implementation;


import com.motorepuestos.melos.data.converter.CitaConverter;
import com.motorepuestos.melos.data.entity.Cita;
import com.motorepuestos.melos.data.model.CitaDTO;
import com.motorepuestos.melos.repository.CitaRepository;
import com.motorepuestos.melos.service.CitaService;
import com.motorepuestos.melos.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaConverter citaConverter;

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Override
    public CitaDTO createCita(CitaDTO citaDTO) {
        Cita cita = citaConverter.dtoToEntity(citaDTO);
        cita.setCliente(usuariosRepository.findById(citaDTO.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        Cita savedCita = citaRepository.save(cita);
        return citaConverter.entityToDto(savedCita);
    }

    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) {
        Cita cita = citaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setFecha(citaDTO.getFecha());
        cita.setHora(citaDTO.getHora());
        cita.setPlacaVehiculo(citaDTO.getPlacavehiculo());
        if (citaDTO.getMecanicoId() != null) {
            cita.setMecanico(usuariosRepository.findById(citaDTO.getMecanicoId()).orElseThrow(() -> new RuntimeException("Mecánico no encontrado")));
        }
        cita.setEstado(citaDTO.getEstado());
        cita.setObservacion(citaDTO.getObservacion());
        cita.setTipoServicio(citaDTO.getTipoServicio());
        cita.setCostoManoObra(citaDTO.getCostoManoObra());
        cita.setCostoProductos(citaDTO.getCostoProductos());
        cita.setCostoTotal(citaDTO.getCostoTotal());
        Cita updatedCita = citaRepository.save(cita);
        return citaConverter.entityToDto(updatedCita);
    }

    @Override
    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public CitaDTO getCitaById(Long id) {
        Cita cita = citaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return citaConverter.entityToDto(cita);
    }

    @Override
    public List<CitaDTO> getAllCitas() {
        return citaRepository.findAll().stream().map(citaConverter::entityToDto).collect(Collectors.toList());
    }
}
