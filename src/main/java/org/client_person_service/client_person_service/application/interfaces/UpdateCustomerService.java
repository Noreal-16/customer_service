package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import reactor.core.publisher.Mono;

public interface UpdateCustomerService {
    Mono<CustomerDTO> updateCustomer(Long id, CustomerDTO data);
}
