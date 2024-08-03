package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import reactor.core.publisher.Flux;

public interface GetAllCustomersService {
    Flux<CustomerDTO> getAllCustomers();
}
