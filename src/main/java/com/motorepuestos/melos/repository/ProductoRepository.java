package com.motorepuestos.melos.repository;

import com.motorepuestos.melos.data.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByStockLessThan(int limit);

    List<Producto> findByCategoriaId(Long categoriaId);


}
