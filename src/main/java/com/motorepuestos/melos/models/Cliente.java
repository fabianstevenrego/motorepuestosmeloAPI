package com.motorepuestos.melos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuarios {
    // Puedes agregar campos específicos para clientes aquí si los hay
}
