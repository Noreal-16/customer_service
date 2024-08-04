package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.RegisterCustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.domain.PersonEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterCustomerImp implements RegisterCustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> mapperConvert;
    private final TransactionalOperator transactionalOperator;


    public Mono<CustomerDTO> save(CustomerDTO data) {
        return savePerson(data)
                .flatMap(personEntity -> {
                    log.info("La data de registrado es {}", personEntity);
                    return saveCustomer(data, personEntity.getId());
                })
                .map(customerEntity -> mapperConvert.toDTO(customerEntity, CustomerDTO.class))
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<ResponseDTO> saveCustomer(CustomerDTO data) {
        return savePerson(data)
                .flatMap(personEntity -> {
                    log.info("La data de registrado es {}", personEntity);
                    return saveCustomer(data, personEntity.getId()).map(customerEntity -> {
                        mapperConvert.toDTO(customerEntity, CustomerDTO.class);
                        return new ResponseDTO(
                                "OK",
                                "Cliente registrado exitosamente"
                        );
                    });
                })
                .as(transactionalOperator::transactional);
    }


    private Mono<PersonEntity> savePerson(CustomerDTO data) {
        PersonEntity personEntity = convertToPersonEntity(data);
        return personRepository.save(personEntity);
    }

    private Mono<CustomerEntity> saveCustomer(CustomerDTO data, Long id) {
        CustomerEntity customerEntity = convertToCustomerEntity(data, id);
        return customerRepository.save(customerEntity);
    }

    private PersonEntity convertToPersonEntity(CustomerDTO data) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(data.getName());
        personEntity.setIdentification(data.getIdentification());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
        return personEntity;
    }

    private CustomerEntity convertToCustomerEntity(CustomerDTO data, Long id) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setPassword(data.getPassword());
        customerEntity.setStatus(data.getStatus());
        customerEntity.setPersonId(id);
        return customerEntity;
    }


}
