package com.motorepuestos.melos.repository;

import com.motorepuestos.melos.data.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
