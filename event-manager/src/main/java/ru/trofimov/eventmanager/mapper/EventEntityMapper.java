package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.entity.RegistrationEntity;
import ru.trofimov.eventmanager.model.Event;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationEntities", ignore = true)
    EventEntity toEntity(Event event);

    @Mapping(target = "participantIds", source = "registrationEntities")
    Event toDomain(EventEntity eventEntity);

    default List<Long> mapRegistrationEntitiesToUserIds(List<RegistrationEntity> registrationEntities) {
        if (registrationEntities == null) {
            return null;
        }

        return registrationEntities.stream()
                .map(RegistrationEntity::getUserId)
                .toList();
    }
}
