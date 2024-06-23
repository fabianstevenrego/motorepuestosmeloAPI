package com.motorepuestos.melos.data.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
public class PedidoDTO {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date fechaIngreso;
    private String proveedor;
    private double costoPedido;
    private List<PedidoProductoModel> pedidoProductos;

}
