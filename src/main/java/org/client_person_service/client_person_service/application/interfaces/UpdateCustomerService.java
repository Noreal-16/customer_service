package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import reactor.core.publisher.Mono;

public interface UpdateCustomerService {
    Mono<ResponseDTO> updateCustomer(Long id, CustomerDTO data);
}
