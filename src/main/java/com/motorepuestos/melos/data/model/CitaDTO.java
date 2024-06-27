package com.motorepuestos.melos.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class CitaDTO {
    private Long idCita;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date fecha;
    private String hora;
    private Long clienteId;
    private String placavehiculo;
    private Long mecanicoId;
    private String estado;
    private String observacion;
    private String tipoServicio;
    private Double costoManoObra;
    private Double costoProductos;
    private Double costoTotal;
}
