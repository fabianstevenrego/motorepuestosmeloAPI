package com.motorepuestos.melos.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipos")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "tipo_id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

}

