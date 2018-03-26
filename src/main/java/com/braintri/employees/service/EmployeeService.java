package com.braintri.employees.service;

import com.braintri.employees.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {


    /**
     *
     * @return
     */
    public List<EmployeeDto> getAllEmployees();

}
