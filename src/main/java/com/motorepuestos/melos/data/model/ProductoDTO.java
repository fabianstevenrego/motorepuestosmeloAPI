package com.motorepuestos.melos.data.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductoDTO {

    private Long id;
    private String codigo;
    private String descripcion;
    private double precioCosto;
    private double precioVenta;
    private Long categoriaId;
    private Long marcaId;
    private Long tipoId;
    private int stock;

    @Data
    public static class DtoAuthRespuesta {
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

    @Data
    public static class DtoLogin {
        private String username;//este es el email pero con otro nombre :)
        private String password;
    }

    @Data
    public static class DtoRegistro {
        private String nombre;
        private String password;
        private String username;
        private String telefono;
        private String tipoUser;
        private String roleName;
        private Integer cedula;
    }

    @Data
    public static class RolDto {
        private Long idRole;
        private String roleNombre;

        public RolDto(Long idRole, String roleNombre) {
            this.idRole = idRole;
            this.roleNombre = roleNombre;
        }
    }
}
