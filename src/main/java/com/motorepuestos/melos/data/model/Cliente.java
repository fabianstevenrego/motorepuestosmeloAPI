package com.motorepuestos.melos.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private User usuario;

    // Otros campos específicos de la entidad Cliente
}
