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
    @NotNull(message = "Gender cannot be null")
    @NotBlank(message = "Gender cannot be empty")
    String gender;
    @NotNull(message = "Age cannot be null")
    Integer age;
    @NotNull(message = "Identification cannot be null")
    @NotBlank(message = "Identification cannot be empty")
    String identification;
    @NotNull(message = "Direction cannot be null")
    @NotBlank(message = "Direction cannot be empty")
    String direction;
    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be empty")
    String phone;
}
