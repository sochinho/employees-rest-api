package com.braintri.employees.exception;

import lombok.Getter;

public class ErrorMessage {

    @Getter
    private String errorDescription;

    public ErrorMessage(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
