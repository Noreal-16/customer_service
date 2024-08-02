package org.client_person_service.client_person_service.infrastructure.repository;

import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Long> {
}
