package org.client_person_service.client_person_service.application.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private String status;
    private String message;
    private T data;
}
