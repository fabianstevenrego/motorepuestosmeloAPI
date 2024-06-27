package com.motorepuestos.melos.data.converter;

import com.motorepuestos.melos.data.entity.Cita;
import com.motorepuestos.melos.data.model.CitaDTO;
import org.springframework.stereotype.Component;

@Component
public class CitaConverter {

    public CitaDTO entityToDto(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setClienteId((long) cita.getCliente().getIdUsuario());
        dto.setPlacavehiculo(cita.getPlacaVehiculo());

        if (cita.getMecanico() != null) {
            dto.setMecanicoId((long) cita.getMecanico().getIdUsuario());
        } else {
            dto.setMecanicoId(null);
        }

        dto.setEstado(cita.getEstado());
        dto.setObservacion(cita.getObservacion());
        dto.setTipoServicio(cita.getTipoServicio());
        dto.setCostoManoObra(cita.getCostoManoObra());
        dto.setCostoProductos(cita.getCostoProductos());
        dto.setCostoTotal(cita.getCostoTotal());
        return dto;
    }

    public Cita dtoToEntity(CitaDTO dto) {
        Cita cita = new Cita();
        cita.setIdCita(dto.getIdCita());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        // Asignar cliente y mecánico según sea necesario
        cita.setPlacaVehiculo(dto.getPlacavehiculo());
        cita.setEstado(dto.getEstado());
        cita.setObservacion(dto.getObservacion());
        cita.setTipoServicio(dto.getTipoServicio());
        cita.setCostoManoObra(dto.getCostoManoObra());
        cita.setCostoProductos(dto.getCostoProductos());
        cita.setCostoTotal(dto.getCostoTotal());
        return cita;
    }
}
