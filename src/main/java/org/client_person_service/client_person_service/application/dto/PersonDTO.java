package org.client_person_service.client_person_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonDTO {
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String gender;
    @NotBlank
    Integer age;
    @NotBlank
    String identification;
    @NotBlank
    String direction;
    @NotBlank
    String phone;
}
