package com.braintri.employees.service;

import com.braintri.employees.converter.CreationDTOEmployeeConverter;
import com.braintri.employees.converter.EmployeeDTOConverter;
import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.dto.EmployeeDTO;
import com.braintri.employees.exception.EmployeeNotFoundException;
import com.braintri.employees.model.Employee;
import com.braintri.employees.repository.EmployeeRepository;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeDTOConverter employeeDTOConverter;

    private CreationDTOEmployeeConverter creationDTOEmployeeConverter;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findEmployees(Predicate predicate) {
        return StreamSupport.stream(employeeRepository.findAll(predicate).spliterator(), false)
                .map(employeeDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeCreationDTO employeeCreationDTO) {
        Employee employee = creationDTOEmployeeConverter.convert(employeeCreationDTO);

        employeeRepository.save(employee);

        return employeeDTOConverter.convert(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        employeeRepository.deleteById(id);
    }
}
