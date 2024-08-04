package org.client_person_service.client_person_service.infrastructure.repository;

import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Long> {

    @Modifying
    @Query("UPDATE customer SET password = :password WHERE id = :id")
    Mono<Void> updatePassword(Long id, String password);
}
