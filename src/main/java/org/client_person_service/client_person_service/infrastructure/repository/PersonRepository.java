package org.client_person_service.client_person_service.infrastructure.repository;

import org.client_person_service.client_person_service.domain.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}
