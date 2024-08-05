package org.client_person_service.client_person_service.infrastructure.exceptions;

import org.springframework.validation.Errors;

public class MethodArgumentNotValidException extends Exception {
    public MethodArgumentNotValidException(String message) {
        super(message);
    }

    public Errors getBindingResult() {

        return null;
    }
}
