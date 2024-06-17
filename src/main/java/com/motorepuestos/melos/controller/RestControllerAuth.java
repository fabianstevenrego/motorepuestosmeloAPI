package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.ProductoDTO;
import com.motorepuestos.melos.data.entity.Cliente;
import com.motorepuestos.melos.data.entity.Empleado;
import com.motorepuestos.melos.data.entity.Roles;
import com.motorepuestos.melos.data.entity.Usuarios;
import com.motorepuestos.melos.repository.IRolesRepository;
import com.motorepuestos.melos.repository.IUsuariosRepository;
import com.motorepuestos.melos.security.JwtGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsuariosRepository usuariosRepository;
    private JwtGenerador jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuariosRepository usuariosRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<String> registrar(@RequestBody ProductoDTO.DtoRegistro dtoRegistro) {
        // Verificar si el usuario ya existe por su nombre de usuario o correo electrónico
        if (usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("El usuario ya existe, intenta con otro nombre de usuario o correo electrónico", HttpStatus.BAD_REQUEST);
        }

        Usuarios usuario;

        // Determinar el tipo de usuario y asignar roles según corresponda
        if (dtoRegistro.getTipoUser().equalsIgnoreCase("EMPLEADO")) {
            // Registrar un empleado
            Empleado empleado = new Empleado();
            empleado.setNombre(dtoRegistro.getNombre());
            empleado.setUsername(dtoRegistro.getUsername());
            empleado.setTelefono(dtoRegistro.getTelefono());
            empleado.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
            empleado.setCedula(dtoRegistro.getCedula()); // Asignar la cédula para empleados

            // Asignar el rol específico al empleado
            Roles rolEmpleado = rolesRepository.findByName(dtoRegistro.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Rol " + dtoRegistro.getRoleName() + " no encontrado en la base de datos"));
            empleado.setRoles(Collections.singletonList(rolEmpleado)); // Asignar el rol específico

            usuario = empleado; // Castear a Usuarios si es necesario, dependiendo de la jerarquía de clases

        } else if (dtoRegistro.getTipoUser().equalsIgnoreCase("CLIENTE")) {
            // Registrar un cliente
            Cliente cliente = new Cliente();
            cliente.setNombre(dtoRegistro.getNombre());
            cliente.setUsername(dtoRegistro.getUsername());
            cliente.setTelefono(dtoRegistro.getTelefono());
            cliente.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

            // Asignar rol CLIENTE al cliente
            Roles clienteRole = rolesRepository.findByName("CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado en la base de datos"));
            cliente.setRoles(Collections.singletonList(clienteRole)); // Asignar el rol CLIENTE

            usuario = cliente; // Castear a Usuarios si es necesario, dependiendo de la jerarquía de clases

        } else {
            // Tipo de usuario desconocido o no manejado
            return new ResponseEntity<>("Tipo de usuario no válido", HttpStatus.BAD_REQUEST);
        }

        // Guardar el usuario en la base de datos
        usuariosRepository.save(usuario);

        // Retornar una respuesta exitosa
        return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody ProductoDTO.DtoLogin dtoLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dtoLogin.getUsername(), dtoLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // No hagas cast a Usuarios aquí, usa los métodos de authentication
            String token = jwtGenerador.generarToken(authentication);

            // Obtener datos del usuario autenticado
            String userType = determineUserType(authentication);
            List<ProductoDTO.RolDto> roles = determineUserRoles(authentication);

            // Construir respuesta DTO
            ProductoDTO.DtoAuthRespuesta authResponse = new ProductoDTO.DtoAuthRespuesta(token, userType, roles);

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    private String determineUserType(Authentication authentication) {
        // Obtener el nombre de usuario autenticado desde el Authentication principal
        String username = authentication.getName();

        // Buscar el tipo de usuario según el nombre de usuario en la base de datos o donde se almacene
        Usuarios usuario = usuariosRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Devolver el tipo de usuario del objeto encontrado
        if (usuario instanceof Empleado) {
            return "EMPLEADO";
        } else if (usuario instanceof Cliente) {
            return "CLIENTE";
        } else {
            return "DESCONOCIDO";
        }
    }

    private List<ProductoDTO.RolDto> determineUserRoles(Authentication authentication) {
        // Obtener el nombre de usuario autenticado desde el Authentication principal
        String username = authentication.getName();

        // Buscar los roles del usuario según el nombre de usuario en la base de datos o donde se almacene
        Usuarios usuario = usuariosRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Devolver los roles del objeto encontrado
        List<ProductoDTO.RolDto> roles = new ArrayList<>();
        for (Roles rol : usuario.getRoles()) {
            roles.add(new ProductoDTO.RolDto(rol.getIdRole(), rol.getName()));
        }

        return roles;
    }

}
