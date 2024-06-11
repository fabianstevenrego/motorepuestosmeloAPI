package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.Role;
import com.motorepuestos.melos.data.model.User;
import com.motorepuestos.melos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Aquí, asumo que ya has configurado el servicio para manejar el registro de usuarios.
        // Para la asignación de roles, utilizamos un conjunto de roles.
        // En este ejemplo, asumimos que el rol del cliente ya está configurado en la base de datos.
        // Puedes ajustar esto según sea necesario para tu aplicación.
        Role clientRole = new Role("ROLE_CLIENTE");
        Set<Role> roles = new HashSet<>();
        roles.add(clientRole);
        user.setRoles(roles);
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser() {
        // Aquí puedes implementar la lógica para el inicio de sesión si es necesario.
        return ResponseEntity.ok("Login successful");
    }
}
