package com.nicoleneza.template.v1.services.impl;

import com.nicoleneza.template.v1.dtos.request.auth.RegisterUserDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserResponseDTO;
import com.nicoleneza.template.v1.enums.ERole;
import com.nicoleneza.template.v1.exceptions.BadRequestException;
import com.nicoleneza.template.v1.models.Employee;
import com.nicoleneza.template.v1.models.Role;
import com.nicoleneza.template.v1.repositories.EmployeeRepository;
import com.nicoleneza.template.v1.services.EmployeeService;
import com.nicoleneza.template.v1.services.IRoleService;
import com.nicoleneza.template.v1.utils.helpers.EmployeeHelper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    public final EmployeeRepository employeeRepository;
    public final IRoleService roleService;
    public final EmployeeHelper employeeHelper;
    public final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;

    public UserResponseDTO createOwner(RegisterUserDTO dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Owner already exists");
        }
        Role role = roleService.getRoleByName(ERole.EMPLOYEE);
        Employee employee = employeeHelper.buildUserFromDto(dto, role, passwordEncoder);

        // Save to DB
        employee = employeeRepository.save(employee);

        // Map to response DTO and return
        return new UserResponseDTO(employee);  // O
    }
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
}
