package org.client_person_service.client_person_service.infrastructure.utils;

import org.springframework.stereotype.Component;

@Component
public class MapperConvert<DTO, ENTITY> {
    public DTO toDTO(ENTITY entity, Class<DTO> dtoClass) {
        return Mapper.modelMapper().map(entity, dtoClass);
    }

    public ENTITY toENTITY(DTO dto, Class<ENTITY> entityClass) {
        return Mapper.modelMapper().map(dto, entityClass);
    }
}
