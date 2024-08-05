package org.client_person_service.client_person_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonDTO {
    Long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotBlank
    String gender;
    @NotNull
    Integer age;
    @NotBlank
    String identification;
    @NotBlank
    String direction;
    @NotBlank
    String phone;
}
