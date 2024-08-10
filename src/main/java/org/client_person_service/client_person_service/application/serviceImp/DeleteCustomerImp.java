package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.DeleteCustomerService;
import org.client_person_service.client_person_service.infrastructure.exceptions.CustomException;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerPersonRepository;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteCustomerImp implements DeleteCustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CustomerPersonRepository customerPersonRepository;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<ResponseDTO> deleteCustomer(long id) {
        return personRepository.findById(id)
                .flatMap(person ->
                        customerPersonRepository.findCustomerByPersonId(person.getId())
                                .flatMap(customer ->
                                        customerRepository.delete(customer)
                                                .then(personRepository.delete(person))
                                ).then(Mono.just(successUpdate()))
                )
                .switchIfEmpty(Mono.error(new CustomException("No se encontró persona con el id " + id, HttpStatus.NOT_FOUND)))
                .as(transactionalOperator::transactional);
    }


    private ResponseDTO successUpdate() {
        return new ResponseDTO(
                "OK",
                "Cliente eliminado exitosamente"
        );
    }

}
