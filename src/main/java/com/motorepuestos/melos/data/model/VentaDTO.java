package com.motorepuestos.melos.data.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VentaDTO {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date fecha;

    private String tipoVenta;
    private Long clienteId;
    private double totalVenta;
    private List<ProductoVentaModel> productosVenta;
}
