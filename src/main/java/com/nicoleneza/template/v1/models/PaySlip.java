package com.nicoleneza.template.v1.models;

import com.nicoleneza.template.v1.enums.EPay;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Month;
import java.util.UUID;

@Entity
@Table(name = "pay_slips")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaySlip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private BigDecimal houseAllowance;
    private BigDecimal transportAllowance;
    private BigDecimal taxedAmount;
    private EPay status;
    private BigDecimal pensionContribution;
    private BigDecimal medicalInsurance;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;

    @Enumerated(EnumType.STRING)
    private Month month;

    private Integer year;

}
