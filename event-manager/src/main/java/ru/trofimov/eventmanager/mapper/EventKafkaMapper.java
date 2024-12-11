package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.common.dto.EventChangeKafkaMessage;
import ru.trofimov.common.enums.EventStatus;
import ru.trofimov.eventmanager.model.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventKafkaMapper {

    @Mapping(target = "eventId", source = "newEvent.id")
    @Mapping(target = "userIds", source = "newEvent.participantIds")
    @Mapping(target = "changedById", ignore = true)
    @Mapping(target = "ownerId", source = "newEvent.ownerId")
    @Mapping(target = "name", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.name(), newEvent.name()))")
    @Mapping(target = "maxPlaces", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.maxPlaces(), newEvent.maxPlaces()))")
    @Mapping(target = "date", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.date(), newEvent.date()))")
    @Mapping(target = "cost", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.cost(), newEvent.cost()))")
    @Mapping(target = "duration", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.duration(), newEvent.duration()))")
    @Mapping(target = "locationId", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.locationId(), newEvent.locationId()))")
    @Mapping(target = "status", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEvent.status(), newEvent.status()))")
    EventChangeKafkaMessage toKafka(Event oldEvent, Event newEvent);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "userIds", source = "event.participantIds")
    @Mapping(target = "changedById", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "maxPlaces", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "locationId", ignore = true)
    @Mapping(target = "status", expression = "java(new ru.trofimov.common.dto.FieldChange<>(oldEventStatus, newEventStatus))")
    EventChangeKafkaMessage toKafkaChangeStatus(Event event, EventStatus oldEventStatus, EventStatus newEventStatus);
}
