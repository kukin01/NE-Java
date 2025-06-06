package com.nicoleneza.template.v1.dtos.request.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserRoleModificationDTO {
    private Set<UUID> roles;
}