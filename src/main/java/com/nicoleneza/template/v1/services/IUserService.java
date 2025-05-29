package com.nicoleneza.template.v1.services;

import com.nicoleneza.template.v1.dtos.request.auth.UpdateUserDTO;
import com.nicoleneza.template.v1.dtos.request.user.CreateAdminDTO;
import com.nicoleneza.template.v1.dtos.request.user.CreateManagerDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserResponseDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserRoleModificationDTO;
import com.nicoleneza.template.v1.models.Employee;
import com.nicoleneza.template.v1.models.Employment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IUserService {

    Employee findUserById(UUID userId);

    Employee getLoggedInUser();
    Employment createEmployment(Employment employment);

    UserResponseDTO createAdmin(CreateAdminDTO createUserDTO);
    Object createManagerEntity(CreateManagerDTO createUserDTO);

    List<Employee> getUsers();

    UserResponseDTO getUserById(UUID uuid);

    UserResponseDTO updateUser(UUID userId, UpdateUserDTO updateUserDTO);

    UserResponseDTO addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    UserResponseDTO removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    void deleteUser(UUID userId);
}
