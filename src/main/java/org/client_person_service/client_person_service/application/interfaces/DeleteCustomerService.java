package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import reactor.core.publisher.Mono;

public interface DeleteCustomerService {

    Mono<ResponseDTO> deleteCustomer(long id);
}
