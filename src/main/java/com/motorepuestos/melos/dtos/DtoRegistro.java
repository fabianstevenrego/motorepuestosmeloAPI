package com.motorepuestos.melos.dtos;

import lombok.Data;

@Data
public class DtoRegistro {
    private String nombre;
    private String password;
    private String username;
    private String telefono;
    private String tipoUser;
    private String roleName;
    private Integer cedula;
}
