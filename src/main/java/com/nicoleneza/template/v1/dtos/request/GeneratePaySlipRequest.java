package com.nicoleneza.template.v1.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Month;
import java.util.UUID;

@Data
public class GeneratePaySlipRequest {
        @NotNull private UUID employeeId;
        @NotNull
        private Month month;
        @Min(2000) @Max(2100) private int year;
}
