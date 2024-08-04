package org.client_person_service.client_person_service.application.interfaces;

import reactor.core.publisher.Mono;

public interface DeleteCustomerService {

    Mono<Void> deleteCustomer(long id);
}
