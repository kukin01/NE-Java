package com.nicoleneza.template.v1.services;

import com.nicoleneza.template.v1.dtos.request.auth.LoginDTO;
import com.nicoleneza.template.v1.dtos.request.auth.RegisterUserDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserResponseDTO;
import com.nicoleneza.template.v1.dtos.response.auth.AuthResponse;

public interface IAuthService {

    AuthResponse login(LoginDTO signInDTO);
    UserResponseDTO register(RegisterUserDTO registerUserDTO);

    void forgotPassword(String email);



    void resetPassword(String email, String passwordResetCode, String newPassword);

    void initiateAccountVerificaton(String email);

    void verifyAccount(String verificationCode);

    void resendVerificationCode(String email);

    void updatePassword(String email, String oldPassword, String newPassword);
}
