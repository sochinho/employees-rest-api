package com.braintri.employees.dto;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PositionDTO {

    private String description;

    private Long numberOfEmployees;
}
