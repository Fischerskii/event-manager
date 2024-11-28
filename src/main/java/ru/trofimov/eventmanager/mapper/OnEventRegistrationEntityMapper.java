package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.entity.RegistrationEntity;
import ru.trofimov.eventmanager.model.Registration;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OnEventRegistrationEntityMapper {

    @Mapping(target = "event.id", source = "eventId")
    RegistrationEntity toEntity(Registration registration);

    @Mapping(target = "eventId", source = "event.id")
    Registration toDomain(RegistrationEntity registration);
}
