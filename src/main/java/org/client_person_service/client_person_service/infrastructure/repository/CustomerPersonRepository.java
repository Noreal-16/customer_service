package org.client_person_service.client_person_service.infrastructure.repository;

import org.client_person_service.client_person_service.domain.CustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerPersonRepository extends R2dbcRepository<CustomerEntity, Long> {

    @Query("select c.*, p.* from customer c inner join person p on p.id = c.person_id ")
    Flux<CustomerEntity> findAllCustomers();

    @Query("select c.*, p.* from customer c inner join person p on p.id = c.person_id where c.id =:id")
    Mono<CustomerEntity> findCustomerById(@Param("id") Long id);

    @Query("select c.*, p.* from customer c inner join person p on p.id = c.person_id where p.identification =:identification")
    Mono<CustomerEntity> findCustomerByIdentification(@Param("identification") String identification);

    @Query("select c.* from customer c where c.person_id =:id")
    Mono<CustomerEntity> findCustomerByPersonId(@Param("id") Long id);
}
