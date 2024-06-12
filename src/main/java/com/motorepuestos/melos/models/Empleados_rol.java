package com.motorepuestos.melos.models;

import com.motorepuestos.melos.models.*;
import jakarta.persistence.FetchType;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados_rol")
public class Empleados_rol {

    @EmbeddedId
    private EmpleadosRolId id; // Clase que representa la clave primaria compuesta

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    private Empleado empleado; // Aquí debes tener una entidad Empleado

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", referencedColumnName = "id_role")
    private Roles rol; // Aquí debes tener una entidad Rol

    // Otros atributos y métodos de la clase...
}
