package com.motorepuestos.melos.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id_empleado")
public class Empleado extends Usuarios {
    @Column(name = "cedula", nullable = false)
    private Integer cedula;
}
