package com.nicoleneza.template.v1.dtos.request.role;

import com.nicoleneza.template.v1.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateRoleDTO {
    @Schema(example = "ADMIN", description = "Role name")
    private ERole name;
}
