package com.motorepuestos.melos.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private String hora;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Usuarios cliente;

    @Column(name = "vehiculo")
    private String placaVehiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mecanico_id")
    private Usuarios mecanico;

    @Column(name = "estado")
    private String estado; // Pendiente, En progreso, Finalizado

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    @Column(name = "costo_mano_obra")
    private Double costoManoObra;

    @Column(name = "costo_productos")
    private Double costoProductos;

    @Column(name = "costo_total")
    private Double costoTotal;

    // Constructor para crear una cita inicialmente
    public Cita(Date fecha, String hora, Usuarios cliente, String placaVehiculo, String observacion, String tipoServicio) {
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.placaVehiculo = placaVehiculo;
        this.observacion = observacion;
        this.tipoServicio = tipoServicio;
        this.estado = "Pendiente"; // Por defecto, la cita se crea como pendiente
    }

    // Métodos getter y setter según sea necesario
}
