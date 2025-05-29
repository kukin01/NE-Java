package com.nicoleneza.template.v1.services;

import com.nicoleneza.template.v1.models.PaySlip;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.UUID;

@Service
public interface PaySlipService {
    PaySlip getPaySlipById(UUID id);
    PaySlip generatePaySlip(UUID employeeId, @NotNull Month month, int year);
}
