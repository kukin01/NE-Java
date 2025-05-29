package com.nicoleneza.template.v1.services.impl;

import com.nicoleneza.template.v1.dtos.request.auth.UpdateUserDTO;
import com.nicoleneza.template.v1.dtos.request.user.CreateAdminDTO;
import com.nicoleneza.template.v1.dtos.request.user.CreateManagerDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserResponseDTO;
import com.nicoleneza.template.v1.dtos.request.user.UserRoleModificationDTO;
import com.nicoleneza.template.v1.enums.ERole;
import com.nicoleneza.template.v1.enums.EStatus;
import com.nicoleneza.template.v1.exceptions.BadRequestException;
import com.nicoleneza.template.v1.exceptions.NotFoundException;
import com.nicoleneza.template.v1.models.Employment;
import com.nicoleneza.template.v1.models.Role;
import com.nicoleneza.template.v1.models.Employee;
import com.nicoleneza.template.v1.repositories.EmployeeRepository;
import com.nicoleneza.template.v1.repositories.EmployementRepository;
import com.nicoleneza.template.v1.repositories.IRoleRepository;
import com.nicoleneza.template.v1.repositories.IUserRepository;
import com.nicoleneza.template.v1.services.IRoleService;
import com.nicoleneza.template.v1.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmployementRepository employementRepository;

    @Value("${application.security.admin.create.code}")
    private String adminCreateCode;
    @Value("${application.security.manager.create.code}")
    private String managerCreateCode;

    public boolean isUserPresent(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public Employee findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + userId));
    }

    @Override
    public Employee getLoggedInUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Employee user = userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundException("User Not Found"));
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        return user;
    }

    public Employee createUserEntity(CreateAdminDTO createAdminDTO) {
        if (isUserPresent(createAdminDTO.getEmail())) {
            throw new BadRequestException("User with the email already exists");
        }

        return Employee.builder()
                .email(createAdminDTO.getEmail())
                .firstName(createAdminDTO.getFirstName())
                .lastName(createAdminDTO.getLastName())
                .fullName(createAdminDTO.getFirstName() + " " + createAdminDTO.getLastName())
                .mobile(createAdminDTO.getPhoneNumber())
                .password(passwordEncoder.encode(createAdminDTO.getPassword()))
                .roles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(ERole.ADMIN))))
                .build();
    }

    public Employee createManagerEntity(CreateManagerDTO createManagerDTO) {
        if (isUserPresent(createManagerDTO.getEmail())) {
            throw new BadRequestException("User with the email already exists");
        }

        return Employee.builder()
                .email(createManagerDTO.getEmail())
                .firstName(createManagerDTO.getFirstName())
                .lastName(createManagerDTO.getLastName())
                .fullName(createManagerDTO.getFirstName() + " " + createManagerDTO.getLastName())
                .mobile(createManagerDTO.getPhoneNumber())
                .password(passwordEncoder.encode(createManagerDTO.getPassword()))
                .roles(new HashSet<>(Collections.singletonList(roleService.getRoleByName(ERole.MANAGER))))
                .build();

    }

    @Override
    @Transactional
    public UserResponseDTO createAdmin(CreateAdminDTO createAdminDTO) {
        if (!createAdminDTO.getAdminCreateCode().equals(adminCreateCode)) {
            throw new BadRequestException("Invalid admin creation code");
        }

        Employee user = createUserEntity(createAdminDTO);
        userRepository.save(user);
        return new UserResponseDTO(user);
    }
    
    @Override
    public List<Employee> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO getUserById(UUID userId) {
        Employee user = findUserById(userId);
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        Employee user = findUserById(userId);

        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setFullName(updateUserDTO.getFirstName() + " " + updateUserDTO.getLastName());
        user.setMobile(updateUserDTO.getPhoneNumber());
        user.setEmail(updateUserDTO.getEmail());

        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO) {
        Employee user = findUserById(userId);
        Set<Role> roles = user.getRoles();
        for (UUID roleId : userRoleModificationDTO.getRoles()) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role not found"));
            roles.add(role);
        }


        user.getRoles().addAll(roles);
        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO) {
        Employee user = findUserById(userId);
        Set<Role> roles = user.getRoles();
        for (UUID roleId : userRoleModificationDTO.getRoles()) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role not found"));
            roles.add(role);
        }

        user.getRoles().removeAll(roles);
        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public Employment createEmployment(Employment employment) {
        Employee employee = employeeRepository.findById(employment.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Employment employement = new Employment();
        employement.setEmployee(employee);
        employment.setDepartment(employement.getDepartment());
        employment.setPosition(employement.getPosition());
        employment.setBaseSalary(employement.getBaseSalary());
        employment.setStatus(EStatus.ACTIVE); // or any other EStatus
        employment.setJoiningDate(employement.getJoiningDate());

        return employementRepository.save(employment);
    }

}

