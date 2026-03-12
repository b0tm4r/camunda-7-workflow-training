package com.example.workflow;

public class BusinessRuleException extends RuntimeException {

    // 1. Debes declarar la variable aquí arriba
    private final String errorCode;

    
    public BusinessRuleException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}