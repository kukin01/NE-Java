package com.nicoleneza.template.v1.dtos.request.auth;


import com.nicoleneza.template.v1.annotations.ValidPassword;
import lombok.Data;

@Data
public class PasswordResetDTO {
    private String email;
    private String resetCode;

    @ValidPassword(message = "Password should be strong")
    private String newPassword;
}