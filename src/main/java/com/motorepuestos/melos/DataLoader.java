package com.motorepuestos.melos;

import com.motorepuestos.melos.data.model.Role;
import com.motorepuestos.melos.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            Arrays.asList("ROLE_ADMIN", "ROLE_AUXILIAR", "ROLE_MECANICO", "ROLE_CLIENTE")
                    .forEach(roleName -> {
                        Role role = new Role();
                        role.setName(roleName);
                        roleRepository.save(role);
                    });
        }
    }
}
