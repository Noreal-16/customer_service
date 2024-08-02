package org.client_person_service.client_person_service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonEntity {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "NOMBRE", nullable = false)
    String name;
    @Column(name = "GENERO", nullable = false)
    String gender;
    @Column(name = "EDAD", nullable = false)
    Integer age;
    @Column(name = "IDENTIFICACION", nullable = false)
    String identification;
    @Column(name = "DIRECCION", nullable = false)
    String direction;
    @Column(name = "TELEFONO", nullable = false)
    String phone;

}
