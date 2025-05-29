package com.nicoleneza.template.v1.dtos.response;

import com.nicoleneza.template.v1.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EmployeeResponseDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String Address;
    private Set<Role> role;
}
