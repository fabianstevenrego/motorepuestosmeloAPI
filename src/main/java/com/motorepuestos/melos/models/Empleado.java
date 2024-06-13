package com.motorepuestos.melos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id_empleado")
public class Empleado extends Usuarios {
    @Column(name = "cedula", nullable = false)
    private Integer cedula;

   /* @ManyToMany
    @JoinTable(
            name = "empleado_rol",
            joinColumns = @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_role")
    )*/
    private List<Roles> roles;

}

