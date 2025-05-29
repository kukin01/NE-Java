package com.nicoleneza.template.v1.dtos;

import com.nicoleneza.template.v1.enums.EStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmploymentDTO {
        private String department;
        private String position;
        private Double baseSalary;
        private LocalDate joiningDate;
        private EStatus status;
}
