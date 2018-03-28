package com.braintri.employees.service;

import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.dto.EmployeeDTO;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface EmployeeService {


    /**
     * @return
     */
    List<EmployeeDTO> getAllEmployees();

    /**
     * @return
     */
    List<EmployeeDTO> findEmployees(Predicate predicate);

    /**
     * @param employeeCreationDTO
     * @return
     */
    EmployeeDTO createEmployee(EmployeeCreationDTO employeeCreationDTO);

    /**
     * @param employeeDTO
     */
    void deleteEmployee(Long id);
}
