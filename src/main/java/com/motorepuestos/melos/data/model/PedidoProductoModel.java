package com.motorepuestos.melos.data.model;

import lombok.Data;

@Data
public class PedidoProductoModel {

    private Long productoId;
    private int cantidad;
    private String nombreProducto;
    private String codigoProducto;
    private  double CostoCompra;
}
