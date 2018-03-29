package com.braintri.employees.converter;

import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.exception.PositionNotFoundException;
import com.braintri.employees.model.Employee;
import com.braintri.employees.model.Position;
import com.braintri.employees.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreationDTOEmployeeConverter implements Converter<EmployeeCreationDTO, Employee> {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Employee convert(EmployeeCreationDTO employeeCreationDTO) {

        Position position = positionRepository.findById(employeeCreationDTO.getPositionId())
                .orElseThrow(PositionNotFoundException::new);

        return Employee.builder()
                .firstName(employeeCreationDTO.getFirstName())
                .lastName(employeeCreationDTO.getLastName())
                .email(employeeCreationDTO.getEmail())
                .position(position)
                .build();
    }
}
