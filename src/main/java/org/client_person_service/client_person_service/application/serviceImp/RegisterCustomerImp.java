package org.client_person_service.client_person_service.application.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.CustomerService;
import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.client_person_service.client_person_service.infrastructure.repository.CustomerRepository;
import org.client_person_service.client_person_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterCustomerImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MapperConvert<CustomerDTO, CustomerEntity> mapperConvert;

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
        CustomerEntity entity = mapperConvert.toENTITY(data, CustomerEntity.class);
        return this.customerRepository.save(entity)
                .flatMap(saveEntity -> Mono.just(mapperConvert.toDTO(saveEntity, CustomerDTO.class)));
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
}
