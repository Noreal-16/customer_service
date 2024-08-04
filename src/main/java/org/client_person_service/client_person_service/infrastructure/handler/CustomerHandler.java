package org.client_person_service.client_person_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerHandler {

    private final GetAllCustomersService getAllCustomersService;
    private final GetInfoCustomerService getInfoCustomerService;
    private final RegisterCustomerService registerCustomerService;
    private final UpdateCustomerService updateCustomerService;
    private final DeleteCustomerService deleteCustomerService;

    public Mono<ServerResponse> getAllCustomers(ServerRequest serverRequest) {
        Flux<CustomerDTO> customerEntityFlux = getAllCustomersService.getAllCustomers();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customerEntityFlux, CustomerDTO.class);
    }

    public Mono<ServerResponse> getInfoCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<CustomerDTO> customerDTOMono = getInfoCustomerService.getInfoCustomer(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customerDTOMono, CustomerDTO.class);
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {
        Mono<CustomerDTO> customerDTOMono = serverRequest.bodyToMono(CustomerDTO.class);
        return customerDTOMono.flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(registerCustomerService.saveCustomer(c), CustomerDTO.class));
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<CustomerDTO> customerDTOMono = serverRequest.bodyToMono(CustomerDTO.class);
        return customerDTOMono.flatMap(cu -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(updateCustomerService.updateCustomer(id, cu), CustomerDTO.class));
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deleteCustomerService.deleteCustomer(id), CustomerDTO.class);
    }

}
