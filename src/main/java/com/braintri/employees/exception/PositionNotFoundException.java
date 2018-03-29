package com.braintri.employees.exception;

import org.springframework.http.HttpStatus;

public class PositionNotFoundException extends BaseException {

    private static final String errorMessage = "Such position does not exist.";

    public PositionNotFoundException() {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
