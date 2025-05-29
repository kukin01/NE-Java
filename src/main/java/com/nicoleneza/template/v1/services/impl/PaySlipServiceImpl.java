package com.nicoleneza.template.v1.services.impl;

import com.nicoleneza.template.v1.dtos.AdminProperties;
import com.nicoleneza.template.v1.enums.EStatus;
import com.nicoleneza.template.v1.enums.IEmailTemplate;
import com.nicoleneza.template.v1.models.Deduction;
import com.nicoleneza.template.v1.models.Employment;
import com.nicoleneza.template.v1.models.PaySlip;
import com.nicoleneza.template.v1.repositories.DeductionRepository;
import com.nicoleneza.template.v1.repositories.EmployementRepository;
import com.nicoleneza.template.v1.services.PaySlipService;
import com.nicoleneza.template.v1.standalone.EmailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaySlipServiceImpl implements PaySlipService {
    private final EmployementRepository employeeRepository;
    private final DeductionRepository deductionRepository;
    private final EmailService emailService;
    private final AdminProperties adminProperties;

    @Override
    public PaySlip getPaySlipById(UUID id) {
        return null;
    }

    @Override
    public PaySlip generatePaySlip(UUID employeeId, Month month, int year) {
        // Get employee and their basic salary
        Employment employment = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // Get all applicable deductions
        List<Deduction> deductions = deductionRepository.findAll();

        // Calculate gross salary (basic salary + allowances)
        BigDecimal basicSalary = BigDecimal.valueOf(employment.getBaseSalary());
        BigDecimal houseAllowance = calculateHouseAllowance(basicSalary);
        BigDecimal transportAllowance = calculateTransportAllowance(basicSalary);
        BigDecimal taxedAmount = calculateTaxedAmount(basicSalary);

        BigDecimal grossSalary = basicSalary
                .add(houseAllowance)
                .add(transportAllowance);

        // Calculate all deductions
        BigDecimal totalDeductions = calculateTotalDeductions(grossSalary, deductions);

        // Calculate net salary
        BigDecimal netSalary = grossSalary.subtract(totalDeductions);

        // Build the PaySlip object
        PaySlip paySlip = PaySlip.builder()
                .employee(employment.getEmployee())
                .month(month)
                .year(year)
                .houseAllowance(houseAllowance)
                .transportAllowance(transportAllowance)
                .grossSalary(grossSalary)
                .taxedAmount(taxedAmount)
                .netSalary(netSalary)
                .build();

        // Email notification logic
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("empId", employment.getEmployee().getId());
            variables.put("name", employment.getEmployee().getFirstName() + " " + employment.getEmployee().getLastName());
            variables.put("status",paySlip.getStatus());
            variables.put("month", month);
            variables.put("year", year);
            variables.put("grossSalary", grossSalary);
            variables.put("taxedAmount", taxedAmount);
            variables.put("houseAllowance", houseAllowance);
            variables.put("transportAllowance", transportAllowance);
            variables.put("totalDeductions", totalDeductions);
            variables.put("netSalary", netSalary);

            emailService.sendEmail(
                    adminProperties.getEmail(),
                    "Admin",
                    "New Payslip Generated - " + employment.getEmployee().getFullName(),
                    IEmailTemplate.PAYSLIP_NOTIFICATION,
                    variables
            );
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging
        }

        return paySlip;
    }

    private BigDecimal calculateTaxedAmount(BigDecimal basicSalary){
        return basicSalary.multiply(BigDecimal.valueOf(0.30));
    }
    private BigDecimal calculateHouseAllowance(BigDecimal basicSalary) {
        // Implement your house allowance calculation logic
        // Example: 20% of basic salary
        return basicSalary.multiply(BigDecimal.valueOf(0.14));
    }

    private BigDecimal calculateTransportAllowance(BigDecimal basicSalary) {
        // Implement your transport allowance calculation logic
        // Example: 10% of basic salary
        return basicSalary.multiply(BigDecimal.valueOf(0.14));
    }

    private BigDecimal calculateTotalDeductions(BigDecimal grossSalary, List<Deduction> deductions) {
        BigDecimal total = BigDecimal.ZERO;

        for (Deduction deduction : deductions) {
            BigDecimal deductionAmount = grossSalary
                    .multiply(BigDecimal.valueOf(deduction.getPercentage()))
                    .divide(BigDecimal.valueOf(100));
            total = total.add(deductionAmount);

            // You might want to set specific deduction fields here
            // based on deduction name/type
        }

        return total;
    }

}