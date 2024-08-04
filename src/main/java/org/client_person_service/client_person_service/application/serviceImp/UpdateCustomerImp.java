package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.UpdateCustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.domain.PersonEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateCustomerImp implements UpdateCustomerService {

    private final PersonRepository personRepository;
    private final CustomerPersonRepository customerPersonRepository;
    private final CustomerRepository customerRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> mapperConvert;

    @Override
    public Mono<CustomerDTO> updateCustomer(Long id, CustomerDTO data) {
        return personRepository.findById(id)
                .flatMap(existPerson -> {
                    log.info("Exist person: {}", existPerson);
                    updatePersonEntity(existPerson, data);
                    return personRepository.save(existPerson)
                            .flatMap(savedPerson -> {
                                log.info("Saved person: {}", savedPerson.getId());
                                return customerPersonRepository.findCustomerByPersonId(savedPerson.getId())
                                        .flatMap(existCustomer -> {
                                            log.info("Exist customer: {}", existCustomer);
                                            existCustomer.setPassword(data.getPassword());
                                            log.info("Exist password: {}", existCustomer.getPassword());
                                            return customerRepository
                                                    .updatePassword(existCustomer.getId(), data.getPassword())
                                                    .then(Mono.just(mapperConvert.toDTO(existCustomer, CustomerDTO.class)));
                                        })
                                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro cliente con el id " + savedPerson.getId())));
                            });
                });
    }


    private void updatePersonEntity(PersonEntity personEntity, CustomerDTO data) {
        personEntity.setName(data.getName());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
    }

    private void updateCustomer(CustomerEntity customerEntity, CustomerDTO data) {
        customerEntity.setPassword(data.getPassword());
    }
}
