package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.PersonDTO;
import org.client_person_service.client_person_service.application.interfaces.CustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.domain.PersonEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterCustomerImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> mapperConvert;
    private final MapperConvert<PersonDTO, PersonEntity> mapperConvertPerson;
    private final TransactionalOperator transactionalOperator;


    @Override
    public Flux<CustomerDTO> findAll() {
        return this.customerRepository.findAll()
                .map(customerEntity -> mapperConvert.toDTO(customerEntity, CustomerDTO.class))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<CustomerDTO> getById(Long id) {
        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe cliente con el ID " + id)))
                .map(customerEntity -> mapperConvert.toDTO(customerEntity, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> save(CustomerDTO data) {
        return savePerson(data)
                .flatMap(personEntity -> {
                    log.info("La data de registrado es {}", personEntity);
                    return saveCustomer(data, personEntity.getId());
                })
                .map(customerEntity -> mapperConvert.toDTO(customerEntity, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> update(Long id, CustomerDTO data) {
        return this.customerRepository.findById(id).flatMap(entity -> {
            entity.setName(data.getName());
            entity.setAge(data.getAge());
            entity.setDirection(data.getDirection());
            entity.setPhone(data.getPhone());
            entity.setGender(data.getGender());
            entity.setPassword(data.getPassword());
            entity.setStatus(data.getStatus());
            return this.customerRepository.save(entity);
        }).map(entityUpdate -> mapperConvert.toDTO(entityUpdate, CustomerDTO.class));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return this.customerRepository.findById(id).publishOn(Schedulers.boundedElastic())
                .doOnSubscribe(customer -> this.customerRepository.delete((CustomerEntity) customer)
                        .subscribe()).then();
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
