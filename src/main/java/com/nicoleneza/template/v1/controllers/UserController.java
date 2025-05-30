package com.nicoleneza.template.v1.controllers;

import com.nicoleneza.template.v1.dtos.request.auth.UpdateUserDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserResponseDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserRoleModificationDTO;
import com.nicoleneza.template.v1.models.Employee;
import com.nicoleneza.template.v1.payload.ApiResponse;
import com.nicoleneza.template.v1.services.IUserService;
import com.nicoleneza.template.v1.utils.ExceptionUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class UserController {

    @Qualifier("IUserService")
    private final IUserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        try {
            List<Employee> users = userService.getUsers();
            List<UserResponseDTO> userDTOs = users.stream()
                    .map(UserResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Users retrieved successfully", HttpStatus.OK, userDTOs);
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to retrieve users", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable UUID userId) {
        try {
            UserResponseDTO user = userService.getUserById(userId);
            return ApiResponse.success("Employee retrieved successfully", HttpStatus.OK, user);
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to retrieve Employee", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{empId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable UUID userId,
            @RequestBody UpdateUserDTO updateUserDTO
    ) {
        try {
            UserResponseDTO updatedUser = userService.updateUser(userId, updateUserDTO);
            return ApiResponse.success("Employee updated successfully", HttpStatus.OK, updatedUser);
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to update user", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{empId}/roles/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> addRoles(
            @PathVariable UUID userId,
            @RequestBody UserRoleModificationDTO userRoleModificationDTO
    ) {
        UserResponseDTO updatedUser = userService.addRoles(userId, userRoleModificationDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/roles/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> removeRoles(
            @PathVariable UUID userId,
            @RequestBody UserRoleModificationDTO userRoleModificationDTO
    ) {
        try {
            UserResponseDTO updatedUser = userService.removeRoles(userId, userRoleModificationDTO);
            return ApiResponse.success("User updated successfully", HttpStatus.OK, updatedUser);
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to remove roles", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to delete user", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getLoggedInUser() {
        try {
            Employee loggedInUser = userService.getLoggedInUser();
            UserResponseDTO userResponseDTO = new UserResponseDTO(loggedInUser);
            return ApiResponse.success("User retrieved successfully", HttpStatus.OK, userResponseDTO);
        } catch (Exception e) {
            ExceptionUtils.handleResponseException(e);
            return ApiResponse.fail("Failed to get user", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
