package com.motorepuestos.melos.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @JoinColumn(name = "fecha", nullable = false)
    private Date fecha;

    @JoinColumn(name = "tipo_venta", nullable = false)
    private String tipoVenta;

    @JoinColumn(name = "cliente_id", nullable = false)
    private Long clienteId;

    @JoinColumn(name = "total_venta", nullable = false)
    private double totalVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoVenta> productosVenta;
}