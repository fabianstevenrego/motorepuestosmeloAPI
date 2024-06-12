package com.motorepuestos.melos.controllers;

import com.motorepuestos.melos.dtos.DtoAuthRespuesta;
import com.motorepuestos.melos.dtos.DtoLogin;
import com.motorepuestos.melos.dtos.DtoRegistro;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro) {
        // Verificar si el usuario ya existe por su nombre de usuario o correo electrónico
        if (usuariosRepository.existsByUsername(dtoRegistro.getUsername()))  {
            return new ResponseEntity<>("El usuario ya existe, intenta con otro nombre de usuario o correo electrónico", HttpStatus.BAD_REQUEST);
        }

        // Crear un nuevo usuario con los datos proporcionados
        Usuarios usuario = new Usuarios();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setEmail(dtoRegistro.getEmail());
        usuario.setTelefono(dtoRegistro.getTelefono());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

        // Guardar el usuario en la base de datos
        usuariosRepository.save(usuario);

        // Asignar roles según corresponde

        // Retornar una respuesta exitosa
        return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
    }


    private void asignarRolesEmpleado(Usuarios usuario, DtoRegistro dtoRegistro) {
        // Obtener el rol AUXILIAR de la base de datos
        Optional<Roles> optionalRolAuxiliar = rolesRepository.findByName("AUXILIAR");

        if (((Optional<?>) optionalRolAuxiliar).isPresent()) {
            // Asignar el rol AUXILIAR al usuario
            Roles rolAuxiliar = optionalRolAuxiliar.get();
            usuario.getRoles().add(rolAuxiliar);
            usuariosRepository.save(usuario);
        } else {
            // Manejar el caso en que el rol AUXILIAR no esté presente en la base de datos
            throw new RuntimeException("No se pudo encontrar el rol AUXILIAR en la base de datos");
        }
    }


    private void asignarRolesUsuario(Usuarios usuario, DtoRegistro dtoRegistro) {
        // Lógica para asignar roles a otros tipos de usuarios (por ejemplo, CLIENTE)
        // rolesRepository.saveAll(rolesAsignados); // rolesAsignados es una lista de roles que puedes definir
    }




    //Método para poder guardar usuarios de tipo ADMIN
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        Roles roles = rolesRepository.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(usuarios);
        return new ResponseEntity<>("Registro de admin exitoso", HttpStatus.OK);
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