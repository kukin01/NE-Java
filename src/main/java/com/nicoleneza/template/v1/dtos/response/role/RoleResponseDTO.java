package com.nicoleneza.template.v1.dtos.response.role;


import com.nicoleneza.template.v1.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleResponseDTO {
    private Role role;
}