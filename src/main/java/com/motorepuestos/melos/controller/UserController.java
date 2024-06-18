package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.motorepuestos.melos.data.model.ResetPasswordDTO;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.reestablecerContrasena(resetPasswordDTO.getEmail(),
                resetPasswordDTO.getActualPassword(),
                resetPasswordDTO.getNewPassword());
    }

    @PostMapping("/email-temp-password")
    public void emailTempPassword(@RequestParam String email) {
        userService.emailContrasena(email);
    }
}

