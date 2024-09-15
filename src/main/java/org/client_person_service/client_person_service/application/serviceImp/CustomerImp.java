package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.CustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.domain.PersonEntity;
import org.client_person_service.client_person_service.infrastructure.exceptions.CustomException;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.client_person_service.client_person_service.infrastructure.utils.CustomerResponseMessage;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerImp implements CustomerService {

    private final CustomerPersonRepository customerPersonRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> customerMapper;
    private final PersonRepository personRepository;
    private final TransactionalOperator transactionalOperator;
    private final CustomerRepository customerRepository;


    @Override
    public Flux<CustomerDTO> getAll() {
        return customerPersonRepository
                .findAllCustomers()
                .map(customerEntity -> customerMapper.toDTO(customerEntity, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> getById(Long id) {
        return customerPersonRepository.findCustomerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada")))
                .map(customerEntity -> customerMapper.toDTO(customerEntity, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> getInfoByIdentification(String identification) {
        return customerPersonRepository.findCustomerByIdentification(identification)
                .switchIfEmpty(Mono.error(new CustomException("No se encontró cliente registrado con la cedula ingresada: " + identification, HttpStatus.NOT_FOUND)))
                .map(existCustomer -> customerMapper.toDTO(existCustomer, CustomerDTO.class));
    }

    @Override
    public Mono<ResponseDTO> register(CustomerDTO data) {
        return savePerson(data)
                .flatMap(personEntity -> {
                    log.info("La data de registrado es {}", personEntity);
                    return saveCustomer(data, personEntity.getId()).map(customerEntity -> {
                        customerMapper.toDTO(customerEntity, CustomerDTO.class);
                        return CustomerResponseMessage.successMessage("registrado");
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

    @Override
    public Mono<ResponseDTO> updateInfoById(CustomerDTO data, Long id) {
        return findUpdatePerson(id, data)
                .flatMap(savedPerson -> findUpdateCustomerByPersonId(savedPerson.getId(), data))
                .then(Mono.just(successUpdate()))
                .as(transactionalOperator::transactional);
    }

    private Mono<PersonEntity> findUpdatePerson(Long id, CustomerDTO data) {
        return personRepository.findById(id).flatMap(personEntity -> {
            log.info("Exist person: {}", personEntity);
            return updatePersonEntity(personEntity, data);
        });
    }

    private Mono<CustomerDTO> findUpdateCustomerByPersonId(Long id, CustomerDTO data) {
        return customerPersonRepository.findCustomerByPersonId(id)
                .flatMap(customerEntity -> {
                    log.info("Exist customer: {}", customerEntity);
                    return updateCustomerByPersonId(customerEntity.getId(), data.getPassword())
                            .then(Mono.just(customerMapper.toDTO(customerEntity, CustomerDTO.class)));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro cliente con el id " + id)));
    }

    private Mono<Void> updateCustomerByPersonId(Long id, String data) {
        return customerRepository.updatePassword(id, data);
    }

    private ResponseDTO successUpdate() {
        return CustomerResponseMessage.successMessage("actualizado");
    }

    private Mono<PersonEntity> updatePersonEntity(PersonEntity personEntity, CustomerDTO data) {
        personEntity.setName(data.getName());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
        return personRepository.save(personEntity);
    }

    @Override
    public Mono<ResponseDTO> deleteInfoById(Long id) {
        return personRepository.findById(id)
                .flatMap(person ->
                        customerPersonRepository.findCustomerByPersonId(person.getId())
                                .flatMap(customer ->
                                        customerRepository.delete(customer)
                                                .then(personRepository.delete(person))
                                ).then(Mono.just(CustomerResponseMessage.successMessage("eliminado")))
                )
                .switchIfEmpty(Mono.error(new CustomException("No se encontró persona con el id " + id, HttpStatus.NOT_FOUND)))
                .as(transactionalOperator::transactional);
    }

}
