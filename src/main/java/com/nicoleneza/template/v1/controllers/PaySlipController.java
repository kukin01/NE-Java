package com.nicoleneza.template.v1.controllers;

import com.nicoleneza.template.v1.dtos.request.GeneratePaySlipRequest;
import com.nicoleneza.template.v1.models.PaySlip;
import com.nicoleneza.template.v1.services.PaySlipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payslips")
public class PaySlipController {
    public final PaySlipService paySlipService;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<PaySlip> generatePaySlip(@Valid @RequestBody GeneratePaySlipRequest request) {
        PaySlip paySlip = paySlipService.generatePaySlip(request.getEmployeeId(), request.getMonth(), request.getYear());
        return ResponseEntity.ok(paySlip);
    }

}
