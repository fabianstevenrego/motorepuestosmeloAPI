package com.motorepuestos.melos.service;

import com.motorepuestos.melos.data.model.Role;
import com.motorepuestos.melos.data.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    void deleteUser(Integer id);

    User getUserById(int id);

    void deleteUser(int id);

    List<Role> getAllRoles();
}
