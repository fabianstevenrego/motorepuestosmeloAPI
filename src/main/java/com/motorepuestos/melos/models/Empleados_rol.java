package com.motorepuestos.melos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados_rol")
public class Empleados_rol {
    @Table(name = "empleados_rol")
    public class empleados_rol {

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "empleado_id", referencedColumnName = "id")
        private Empleado empleado; // Aquí debes tener una entidad Empleado

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "rol_id", referencedColumnName = "id")
        private Roles rol; // Aquí debes tener una entidad Rol
    }
}