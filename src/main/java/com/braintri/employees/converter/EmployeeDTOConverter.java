package com.braintri.employees.converter;

import com.braintri.employees.dto.EmployeeDTO;
import com.braintri.employees.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeDTOConverter implements Converter<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO convert(Employee employee) {

        return EmployeeDTO.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .positionId(employee.getPosition().getId())
                .build();
    }
}
