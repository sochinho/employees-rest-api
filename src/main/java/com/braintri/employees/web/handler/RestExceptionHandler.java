package com.braintri.employees.web.handler;

import com.braintri.employees.exception.BaseException;
import com.braintri.employees.exception.EmployeeNotFoundException;
import com.braintri.employees.exception.PositionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class, PositionNotFoundException.class})
    public ResponseEntity handleNoSuchObject(BaseException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getErrorMessage());
    }
}
