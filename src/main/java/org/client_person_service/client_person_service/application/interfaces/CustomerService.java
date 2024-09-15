package org.client_person_service.client_person_service.application.interfaces;

import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> getAll();

    Mono<CustomerDTO> getById(Long id);

    Mono<CustomerDTO> getInfoByIdentification(String identification);

    Mono<ResponseDTO> register(CustomerDTO data);

    Mono<ResponseDTO> updateInfoById(CustomerDTO id, Long data);

    Mono<ResponseDTO> deleteInfoById(Long id);
}
