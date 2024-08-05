package org.client_person_service.client_person_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.*;
import org.client_person_service.client_person_service.infrastructure.exceptions.ErrorResponseCustomer;
import org.client_person_service.client_person_service.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@Validated
@RequiredArgsConstructor
public class CustomerHandler {

    private final GetAllCustomersService getAllCustomersService;
    private final GetInfoCustomerService getInfoCustomerService;
    private final RegisterCustomerService registerCustomerService;
    private final UpdateCustomerService updateCustomerService;
    private final DeleteCustomerService deleteCustomerService;

    public Mono<ServerResponse> getAllCustomers(ServerRequest serverRequest) {
        return getAllCustomersService.getAllCustomers()
                .collectList()
                .flatMap(customers -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(customers))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }

    public Mono<ServerResponse> getInfoCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return getInfoCustomerService.getInfoCustomer(id)
                .flatMap(customerDTO -> ServerResponse.ok().bodyValue(customerDTO))
                .onErrorResume(ResourceNotFoundException.class, e -> ServerResponse.status(HttpStatus.NOT_FOUND)
                        .bodyValue(new ErrorResponseCustomer("NOT_FOUND", e.getMessage())));
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {
        Mono<CustomerDTO> customerDTOMono = serverRequest.bodyToMono(CustomerDTO.class);
        return customerDTOMono.flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(registerCustomerService.saveCustomer(c), CustomerDTO.class)
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers"))));
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<CustomerDTO> customerDTOMono = serverRequest.bodyToMono(CustomerDTO.class);
        return customerDTOMono.flatMap(cu -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(updateCustomerService.updateCustomer(id, cu), CustomerDTO.class))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deleteCustomerService.deleteCustomer(id), CustomerDTO.class)
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }

}
