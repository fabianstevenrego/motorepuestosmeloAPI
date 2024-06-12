package com.motorepuestos.melos.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DtoRegistro {
    private String username;
    private String password;
    private String email;
    private String telefono;
}
