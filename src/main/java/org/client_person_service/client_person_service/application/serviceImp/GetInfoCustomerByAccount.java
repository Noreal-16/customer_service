package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.GetInfoCustomerByIdentification;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetInfoCustomerByAccount implements GetInfoCustomerByIdentification {


    private final CustomerPersonRepository customerPersonRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> customerMapper;

    @Override
    public Mono<CustomerDTO> getInfoCustomerByIdentification(String identification) {
        return customerPersonRepository.findCustomerByIdentification(identification)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró cliente registrado con la cedula ingresada: " + identification)))
                .map(existCustomer -> customerMapper.toDTO(existCustomer, CustomerDTO.class));
    }
}
