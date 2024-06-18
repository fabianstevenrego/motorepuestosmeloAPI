package com.motorepuestos.melos.data.model;

import lombok.Data;

import java.util.List;

@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String userType;
    private List<RolDto> rol;

    public DtoAuthRespuesta(String accessToken, String userType, List<RolDto> rol) {
        this.accessToken = accessToken;
        this.userType = userType;
        this.rol = rol;
    }
}