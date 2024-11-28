package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.model.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationEntities", ignore = true)
    EventEntity toEntity(Event event);
    Event toDomain(EventEntity eventEntity);
}
