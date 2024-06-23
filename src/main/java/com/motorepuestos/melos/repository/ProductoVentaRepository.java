package com.motorepuestos.melos.repository;


import com.motorepuestos.melos.data.entity.ProductoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, Long> {
}