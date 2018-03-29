package com.braintri.employees.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends BaseException {

    private static final String errorMessage = "Such employee has not found.";

    public EmployeeNotFoundException() {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
