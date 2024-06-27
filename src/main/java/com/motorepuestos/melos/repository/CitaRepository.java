package com.motorepuestos.melos.repository;

import com.motorepuestos.melos.data.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario
}