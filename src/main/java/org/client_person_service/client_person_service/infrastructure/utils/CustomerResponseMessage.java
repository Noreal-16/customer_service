package org.client_person_service.client_person_service.infrastructure.utils;

import org.client_person_service.client_person_service.application.dto.ResponseDTO;

public class CustomerResponseMessage {


    public static ResponseDTO successMessage(String operation) {
        return new ResponseDTO(
                "OK",
                "Cliente " +operation+"  exitosamente"
        );
    }
}
