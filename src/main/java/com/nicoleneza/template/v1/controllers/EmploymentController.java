package com.nicoleneza.template.v1.controllers;

import com.nicoleneza.template.v1.dtos.EmploymentDTO;
import com.nicoleneza.template.v1.models.Employment;
import com.nicoleneza.template.v1.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmploymentController {
    private final UserServiceImpl userService;

    @PostMapping("/{employeeId}")
    public ResponseEntity<Employment> createEmployment(
            @RequestBody Employment employment
    ) {
        Employment employyment = userService.createEmployment(employment);
        return ResponseEntity.ok(employment);
    }
}
