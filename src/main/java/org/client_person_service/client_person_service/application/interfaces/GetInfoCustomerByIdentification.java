package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import reactor.core.publisher.Mono;

public interface GetInfoCustomerByIdentification {
    Mono<CustomerDTO> getInfoCustomerByIdentification(String identification);
}
