package com.nicoleneza.template.v1.utils.helpers;


import com.nicoleneza.template.v1.dtos.request.auth.RegisterUserDTO;
import com.nicoleneza.template.v1.models.Employee;
import com.nicoleneza.template.v1.models.Role;
import com.nicoleneza.template.v1.dtos.request.auth.RegisterUserDTO;
import com.nicoleneza.template.v1.models.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EmployeeHelper {

    public Employee buildUserFromDto(RegisterUserDTO dto, Role role, PasswordEncoder passwordEncoder){
        return  Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .fullName(dto.getFirstName() + " " + dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .mobile(dto.getPhoneNumber())
                .roles(Set.of(role))
                .build();
    }
}
