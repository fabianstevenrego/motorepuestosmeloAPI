package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.motorepuestos.melos.data.model.ResetPasswordDTO;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * este endpoint se utiliza para cambiar de clave
     * re valida el email, clave-actual y se recibe la nueva clave
     */
    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.reestablecerContrasena(resetPasswordDTO.getEmail(),
                resetPasswordDTO.getActualPassword(),
                resetPasswordDTO.getNewPassword());
    }

    /**
     * este endpoint se utiliza para enviar una nueva clave
     * esta se envia al email proporcionado, este email se verifica que exista en la BD
     */
    @PostMapping("/email-temp-password")
    public ResponseEntity<Map<String, String>> emailTempPassword(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.emailContrasena(email);
            response.put("message", "La nueva contraseña ha sido enviada a tu correo electrónico.");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            response.put("message", "No existe el usuario con el correo electrónico proporcionado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al intentar enviar el correo electrónico.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Manejo de excepciones específicas
    @ExceptionHandler({IllegalArgumentException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

