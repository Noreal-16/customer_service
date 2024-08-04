package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.UpdateCustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.domain.PersonEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
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
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<ResponseDTO> updateCustomer(Long id, CustomerDTO data) {
        return findUpdatePerson(id, data)
                .flatMap(savedPerson -> findUpdateCustomerByPersonId(savedPerson.getId(), data))
                .then(Mono.just(successUpdate()))
                .as(transactionalOperator::transactional);
    }

    private Mono<PersonEntity> findUpdatePerson(Long id, CustomerDTO data) {
        return personRepository.findById(id).flatMap(personEntity -> {
            log.info("Exist person: {}", personEntity);
            updatePersonEntity(personEntity, data);
            return personRepository.save(personEntity);
        });
    }

    private Mono<CustomerDTO> findUpdateCustomerByPersonId(Long id, CustomerDTO data) {
        return customerPersonRepository.findCustomerByPersonId(id)
                .flatMap(customerEntity -> {
                    log.info("Exist customer: {}", customerEntity);
                    return updateCustomer(customerEntity.getId(), data)
                            .then(Mono.just(mapperConvert.toDTO(customerEntity, CustomerDTO.class)));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro cliente con el id " + id)));
    }

    private ResponseDTO successUpdate() {
        return new ResponseDTO(
                "OK",
                "Cliente actualizado exitosamente"
        );
    }

    private void updatePersonEntity(PersonEntity personEntity, CustomerDTO data) {
        personEntity.setName(data.getName());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
    }

    private Mono<Void> udpCustomer(Long id, CustomerDTO data) {
        return customerRepository.updatePassword(id, data.getPassword());
    }
}
