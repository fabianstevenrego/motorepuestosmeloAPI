package com.motorepuestos.melos.controllers;

import com.motorepuestos.melos.dtos.DtoAuthRespuesta;
import com.motorepuestos.melos.dtos.DtoLogin;
import com.motorepuestos.melos.dtos.DtoRegistro;
import com.motorepuestos.melos.models.Cliente;
import com.motorepuestos.melos.models.Empleado;
import com.motorepuestos.melos.models.Roles;
import com.motorepuestos.melos.models.Usuarios;
import com.motorepuestos.melos.repositories.IRolesRepository;
import com.motorepuestos.melos.repositories.IUsuariosRepository;
import com.motorepuestos.melos.security.JwtGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

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
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro) {
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

            asignarRolesEmpleado(empleado);

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
            cliente.getRoles().add(clienteRole);

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

    private void asignarRolesEmpleado(Empleado empleado) {
        // Inicializar la lista de roles si es nula
        if (empleado.getRoles() == null) {
            empleado.setRoles(new ArrayList<>());
        }

        // Asignar roles específicos para empleados (ADMIN, AUXILIAR, MECANICO)
        // Obtener los roles de la base de datos por su nombre
        Roles admin = rolesRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado en la base de datos"));
        Roles auxiliar = rolesRepository.findByName("AUXILIAR")
                .orElseThrow(() -> new RuntimeException("Rol AUXILIAR no encontrado en la base de datos"));
        Roles mecanico = rolesRepository.findByName("MECANICO")
                .orElseThrow(() -> new RuntimeException("Rol MECANICO no encontrado en la base de datos"));

        empleado.getRoles().add(admin);
        empleado.getRoles().add(auxiliar);
        empleado.getRoles().add(mecanico);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }
}
