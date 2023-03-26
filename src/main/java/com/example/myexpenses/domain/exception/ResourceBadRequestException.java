package com.example.myexpenses.domain.exception;

public class ResourceBadRequestException extends RuntimeException {
    
    public ResourceBadRequestException(String message) {
        super(message);
    }
}
