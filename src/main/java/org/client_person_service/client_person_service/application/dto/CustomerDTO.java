package org.client_person_service.client_person_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends PersonDTO {
    Long customerId;
    @NotBlank
    String password;
    Boolean status;
}
