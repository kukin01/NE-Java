package com.nicoleneza.template.v1.repositories;

import com.nicoleneza.template.v1.models.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaySlipRepository extends JpaRepository<PaySlip, UUID> {
}
