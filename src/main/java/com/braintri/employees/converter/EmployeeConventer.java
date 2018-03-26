package com.braintri.employees.converter;

import com.braintri.employees.dto.EmployeeDto;
import com.braintri.employees.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class EmployeeConventer implements Converter<Employee, EmployeeDto> {

    @Override
    public EmployeeDto convert(Employee employee) {

        return EmployeeDto.builder()
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .positionId(employee.getPosition().getId())
                .build();
    }
}
