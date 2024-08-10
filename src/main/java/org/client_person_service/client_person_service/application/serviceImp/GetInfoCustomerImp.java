package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.GetInfoCustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.infrastructure.exceptions.CustomException;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetInfoCustomerImp implements GetInfoCustomerService {

    private final CustomerPersonRepository customerRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> mapperConvert;

    @Override
    public Mono<CustomerDTO> getInfoCustomer(Long id) {
        return customerRepository.findCustomerById(id)
                .switchIfEmpty(Mono.error(new CustomException("No existe cliente con el id: " + id, HttpStatus.NOT_FOUND)))
                .map(infoCustomer -> mapperConvert.toDTO(infoCustomer, CustomerDTO.class));
    }
}
