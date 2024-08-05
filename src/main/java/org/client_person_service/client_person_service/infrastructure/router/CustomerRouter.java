package org.client_person_service.client_person_service.infrastructure.router;

import lombok.extern.slf4j.Slf4j;
import org.client_person_service.client_person_service.infrastructure.handler.CustomerHandler;
import org.client_person_service.client_person_service.infrastructure.router.docs.CustomersOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class CustomerRouter {

    private static final String PATH = "/api/v1/customers";

    @Bean
    @CustomersOpenApi
    RouterFunction<ServerResponse> routes(CustomerHandler customerHandler) {
        return RouterFunctions.route()
                .GET(PATH, customerHandler::getAllCustomers)
                .GET(PATH + "/{id}", customerHandler::getInfoCustomer)
                .POST(PATH, customerHandler::createCustomer)
                .PUT(PATH + "/{id}", customerHandler::updateCustomer)
                .DELETE(PATH + "/{id}", customerHandler::deleteCustomer)
                .build();
    }

}
