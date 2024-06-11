package com.motorepuestos.melos.repository;

import com.motorepuestos.melos.data.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
