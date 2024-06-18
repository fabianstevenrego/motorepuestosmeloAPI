package com.motorepuestos.melos.service;

import com.motorepuestos.melos.data.entity.Usuarios;
import com.motorepuestos.melos.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private IUsuariosRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Reestablecer contraseña de cualquier usuario.
     *
     * @param email      email del usuario al que se le asignara la nueva
     *                   contraseña.
     * @param actualContraseña actual contraseña del usuario.
     * @param nuevaContraseña nueva contraseña a asignar.
     * @return boolean Refleja si la operacion tuvo exito.
     */
    public void reestablecerContrasena(String email, String actualContraseña, String nuevaContraseña) {

        Usuarios user = userRepository.findByUsername(email).orElseThrow(() ->
                new UsernameNotFoundException("No existe el usuario con el correo electrónico proporcionado")
        );

        if (nuevaContraseña.equals(user.getUsername())){
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual al correo electrónico");
        }

        if (!bCryptPasswordEncoder.matches(actualContraseña, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }

        user.setPassword(bCryptPasswordEncoder.encode(nuevaContraseña));
        userRepository.save(user);
    }

    /**
     * Establece contraseña temporal aleatoria al usuario y la envia a traves de
     * correo electronico.
     *
     * @param email email del usuario al que se le asignara la contraseña.
     * @return boolean Refleja si la operacion tuvo exito.
     */
    public void emailContrasena(String email) {

        Usuarios user = userRepository.findByUsername(email).orElseThrow(() ->
                new UsernameNotFoundException("No existe el usuario con el correo electrónico proporcionado")
        );

        String tempcontrasena = generarContrasena(10);
        user.setPassword(bCryptPasswordEncoder.encode(tempcontrasena));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el usuario");
        }

        emailService.sendListEmail(email, "Contraseña de Recuperación", tempcontrasena);
    }

    /**
     * Genera una contraseña temporal aleatoria.
     *
     * @param length longitud de la contraseña generada.
     * @return contraseña generada.
     */
    private String generarContrasena(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
