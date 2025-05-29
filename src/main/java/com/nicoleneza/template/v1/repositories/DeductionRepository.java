package com.nicoleneza.template.v1.repositories;

import com.nicoleneza.template.v1.models.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, UUID> {
}