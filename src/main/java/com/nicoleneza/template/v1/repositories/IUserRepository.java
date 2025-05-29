package com.nicoleneza.template.v1.repositories;

import com.nicoleneza.template.v1.models.Role;
import com.nicoleneza.template.v1.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface IUserRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findUserByEmail(String email);

    Optional<Employee> findByRoles(Role role);

    Optional<Employee> findByVerificationCode(String verificationCode);

    boolean existsByEmail(String email);
}
