package com.braintri.employees.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Builder
public class EmployeeDto {

    private String firstName;

    private String lastName;

    private String email;

    private Long positionId;
}
