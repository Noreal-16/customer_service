package org.client_person_service.client_person_service.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.client_person_service.client_person_service.application.interfaces.GetAllCustomersService;
import org.client_person_service.client_person_service.application.interfaces.GetInfoCustomerService;
import org.client_person_service.client_person_service.application.interfaces.RegisterCustomerService;
import org.client_person_service.client_person_service.application.interfaces.UpdateCustomerService;
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
    public Mono<CustomerDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        return registerCustomerService.save(customerDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return updateCustomerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return registerCustomerService.delete(id);
    }

}
