package com.braintri.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO extends EmployeeCreationDTO {

    private Long id;

    @Builder
    public EmployeeDTO(Long id, String firstName, String lastName, String email, Long positionId) {
        super(firstName, lastName, email, positionId);
        this.id = id;
    }
}
