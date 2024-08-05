package org.client_person_service.client_person_service.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseCustomer {
    private String errorCode;
    private String errorMessage;
}
