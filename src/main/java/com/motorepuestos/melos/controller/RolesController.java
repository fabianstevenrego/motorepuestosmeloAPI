package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.Role;
import com.motorepuestos.melos.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    @Secured("permitAll") // Otra forma de permitir el acceso libre
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
