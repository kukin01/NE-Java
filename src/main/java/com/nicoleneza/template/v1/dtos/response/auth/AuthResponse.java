package com.nicoleneza.template.v1.dtos.response.auth;

import com.nicoleneza.template.v1.models.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private Employee user;


    public AuthResponse(String token, Employee user) {
        this.token = token;
        this.user = user;
    }
}