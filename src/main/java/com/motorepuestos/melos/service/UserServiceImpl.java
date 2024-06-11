package com.motorepuestos.melos.service;

import com.motorepuestos.melos.data.model.Role;
import com.motorepuestos.melos.data.model.User;
import com.motorepuestos.melos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public List<Role> getAllRoles() {
        // Aquí deberías implementar la lógica para obtener todos los roles de tu base de datos
        return null;
    }
}
