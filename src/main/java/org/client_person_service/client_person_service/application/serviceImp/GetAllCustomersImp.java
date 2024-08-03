package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.GetAllCustomersService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetAllCustomersImp implements GetAllCustomersService {

    private final CustomerPersonRepository customerPersonRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> customerMapper;

    @Override
    public Flux<CustomerDTO> getAllCustomers() {
        return customerPersonRepository.findAllCustomers().map(customerEntity -> customerMapper.toDTO(customerEntity, CustomerDTO.class));
    }
}
