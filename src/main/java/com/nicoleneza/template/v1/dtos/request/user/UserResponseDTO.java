package com.nicoleneza.template.v1.dtos.request.user;


import com.nicoleneza.template.v1.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Employee user;
}