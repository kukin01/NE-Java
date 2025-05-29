package com.nicoleneza.template.v1.repositories;

import com.nicoleneza.template.v1.models.Employment;
import com.nicoleneza.template.v1.models.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployementRepository extends JpaRepository<Employment, UUID> {

    Optional<Employment> findById(UUID employeeId);
}
