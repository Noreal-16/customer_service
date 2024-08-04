package org.client_person_service.client_person_service.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.dto.ResponseDTO;
import org.client_person_service.client_person_service.application.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final RegisterCustomerService registerCustomerService;
    private final GetAllCustomersService getAllCustomersService;
    private final GetInfoCustomerService getInfoCustomerService;
    private final UpdateCustomerService updateCustomerService;
    private final DeleteCustomerService deleteCustomerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDTO> getAllCustomers() {
        return getAllCustomersService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return getInfoCustomerService.getInfoCustomer(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        return registerCustomerService.saveCustomer(customerDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return updateCustomerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseDTO> deleteCustomer(@PathVariable Long id) {
        return deleteCustomerService.deleteCustomer(id);
    }

}
