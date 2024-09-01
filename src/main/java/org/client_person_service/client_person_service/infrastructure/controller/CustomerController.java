package org.client_person_service.client_person_service.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.CustomerService;
import org.client_person_service.client_person_service.infrastructure.utils.docs.CustomersOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@CustomersOpenApi
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDTO> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/{identification}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> getInfoCustomer(@PathVariable String identification) {
        return customerService.getInfoById(identification);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> registerCustomer(@RequestBody @Validated CustomerDTO customerDTO) {
        return customerService.register(customerDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> updateCustomer(@RequestBody @Validated CustomerDTO customerDTO, @PathVariable Long id) {
        return customerService.updateInfoById(customerDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseDTO> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteInfoById(id);
    }


}
