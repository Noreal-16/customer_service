package org.client_person_service.client_person_service.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "CUSTOMER")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity extends PersonEntity {


    @Column
    String password;

    @Column
    Boolean status;

    @Column(value = "PERSON_ID")
    Long personId;

}
