package org.client_person_service.client_person_service.infrastructure.utils.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.client_person_service.client_person_service.application.dto.CustomerDTO;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                method = RequestMethod.GET,
                path = "/api/v1/customers",
                operation =
                @Operation(
                        description = "Obtener todos los clientes.",
                        operationId = "getAll",
                        tags = "Clientes",
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Todos los clientes.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))
                                        })
                        })),

        @RouterOperation(
                method = RequestMethod.GET,
                path = "/api/v1/customers/{id}",
                operation =
                @Operation(
                        description = "Obtener información del cliente por su ID.",
                        operationId = "getById",
                        tags = "Clientes",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cliente encontrado.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = CustomerDTO.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/api/v1/customers",
                operation =
                @Operation(
                        description = "Registrar un cliente.",
                        operationId = "create",
                        tags = "Clientes",
                        requestBody =
                        @RequestBody(
                                description = "Body para crear un cliente.",
                                required = true,
                                content = @Content(schema = @Schema(implementation = CustomerDTO.class,
                                        requiredProperties = {" name", "gender", "age", "identification", "direction", "phone", "password"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cliente registrado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = CustomerDTO.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.PUT,
                path = "/api/v1/customers/{id}",
                operation =
                @Operation(
                        description = "Actualizar un cliente.",
                        operationId = "update",
                        tags = "Clientes",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        requestBody =
                        @RequestBody(
                                description = ".",
                                required = true,
                                content = @Content(schema = @Schema(implementation = CustomerDTO.class,
                                        requiredProperties = {"name", "gender", "age",  "direction", "phone", "password"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cliente actualizado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = CustomerDTO.class))
                                        })
                        })),




        @RouterOperation(
                method = RequestMethod.DELETE,
                path = "/api/v1/customers/{id}",
                operation =
                @Operation(
                        description = "Eliminar un cliente por su ID.",
                        operationId = "delete",
                        tags = "Clientes",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "cliente eliminado con éxito.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE
                                                )})
                        }))
})
public @interface CustomersOpenApi {
}
