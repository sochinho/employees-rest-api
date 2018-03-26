package com.braintri.employees.service;

import com.braintri.employees.converter.EmployeeConventer;
import com.braintri.employees.dto.EmployeeDto;
import com.braintri.employees.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeConventer employeeConventer;

    @Transactional
    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeConventer::convert).collect(Collectors.toList());
    }
}
