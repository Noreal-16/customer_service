package org.client_person_service.client_person_service.application.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService<T> {


    Flux<T> findAll();

    Mono<T> getById(Long id);

    Mono<T> save(T data);

    Mono<T> update(Long id, T data);

    Mono<Void> delete(Long id);

}
