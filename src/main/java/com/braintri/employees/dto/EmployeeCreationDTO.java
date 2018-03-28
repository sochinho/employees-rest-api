package com.braintri.employees.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeCreationDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotNull
    @Min(0)
    private Long positionId;
}
