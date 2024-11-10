package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.dto.EventCreateRequestDTO;
import ru.trofimov.eventmanager.dto.EventUpdateRequestDTO;
import ru.trofimov.eventmanager.model.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventDtoMapper {

    EventCreateRequestDTO toCreateDto(Event event);
    EventUpdateRequestDTO toUpdateDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "occupiedPlaces", ignore = true)
    @Mapping(target = "status", ignore = true)
    Event toDomain(EventCreateRequestDTO eventDto);
    Event toDomain(EventUpdateRequestDTO eventDto);
}
