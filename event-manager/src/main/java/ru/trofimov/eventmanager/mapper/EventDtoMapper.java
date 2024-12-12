package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.dto.EventCreateRequestDTO;
import ru.trofimov.eventmanager.dto.EventDTO;
import ru.trofimov.eventmanager.dto.EventSearchRequestDTO;
import ru.trofimov.eventmanager.dto.EventUpdateRequestDTO;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.model.EventFilter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventDtoMapper {

    EventDTO toDTO(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "occupiedPlaces", constant = "0")
    @Mapping(target = "status", expression = "java(ru.trofimov.common.enums.EventStatus.WAIT_START)")
    Event toDomain(EventCreateRequestDTO eventDto);

    Event toDomain(EventUpdateRequestDTO eventDto);


    EventFilter toDomain(EventSearchRequestDTO eventDto);
}
