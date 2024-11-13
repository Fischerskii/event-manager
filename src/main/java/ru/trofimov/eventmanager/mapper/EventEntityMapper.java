package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.model.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventEntityMapper {

    EventEntity toEntity(Event event);
    Event toDomain(EventEntity eventEntity);
}
