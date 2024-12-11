package ru.trofimov.eventnotificator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.trofimov.eventnotificator.dto.EventNotificationDTO;
import ru.trofimov.eventnotificator.entity.UserEventNotificationEntity;
import ru.trofimov.eventnotificator.model.UserEventNotification;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EventNotificationMapper.class)
public interface UserEventNotificationMapper {

    @Mapping(target = "eventNotification", source = "eventNotification")
    @Mapping(target = "isRead", source = "read")
    UserEventNotification toDomain(UserEventNotificationEntity entity);

    @Mapping(source = "eventNotification", target = ".")
    EventNotificationDTO toDTO(UserEventNotification notification);
}
