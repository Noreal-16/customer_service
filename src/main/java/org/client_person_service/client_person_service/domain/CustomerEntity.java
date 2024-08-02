package org.client_person_service.client_person_service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "CLIENTE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity extends PersonEntity {

    @Id
    @Column(name = "CLIENTEID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long customerId;

    @Column(name = "CONTRASENIA", nullable = false)
    String password;

    @Column(name = "ESTADO", columnDefinition = "boolean default true")
    Boolean status;


}
