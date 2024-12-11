package ru.trofimov.eventnotificator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.common.dto.EventChangeKafkaMessage;
import ru.trofimov.eventnotificator.dto.EventNotificationDTO;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;
import ru.trofimov.eventnotificator.model.EventNotification;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventNotificationMapper {

    EventNotification toDomain(EventNotificationEntity entity);

    @Mapping(target = "id", ignore = true)
    EventNotificationEntity toEntity(EventNotification domain);

    EventNotification fromKafka(EventChangeKafkaMessage message);

    EventNotificationDTO toDTO(EventNotification domain);

//    EventNotification toDomain(EventNotificationDTO dto);
}
