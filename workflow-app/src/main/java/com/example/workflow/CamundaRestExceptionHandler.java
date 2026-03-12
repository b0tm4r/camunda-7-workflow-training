package com.example.workflow;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE) // <--- CRITICO: Prioridad máxima
@ControllerAdvice
public class CamundaRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        
        // Buscamos nuestra BusinessRuleException en toda la jerarquía de la excepción
        Throwable cause = ex;
        while (cause != null) {
            if (cause instanceof BusinessRuleException) {
                BusinessRuleException bizEx = (BusinessRuleException) cause;
                return new ResponseEntity<>(
                    new ErrorResponse(bizEx.getErrorCode(), bizEx.getMessage()), 
                    HttpStatus.UNPROCESSABLE_ENTITY
                );
            }
            cause = cause.getCause();
        }

        // Si no es nuestra excepción, dejamos que el error técnico pase (o lo limpias aquí)
        // Para pruebas, si quieres limpiar TODO, cambia lo de abajo por un mensaje fijo
        return new ResponseEntity<>(
            new ErrorResponse("TECHNICAL_ERROR", ex.getMessage()), 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public static class ErrorResponse {
        private String code;
        private String message;
        public ErrorResponse(String code, String message) { this.code = code; this.message = message; }
        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}