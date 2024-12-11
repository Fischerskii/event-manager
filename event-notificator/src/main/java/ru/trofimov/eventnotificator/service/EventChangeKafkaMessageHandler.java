package ru.trofimov.eventnotificator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.common.dto.EventChangeKafkaMessage;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;
import ru.trofimov.eventnotificator.entity.UserEventNotificationEntity;
import ru.trofimov.eventnotificator.mapper.EventNotificationMapper;
import ru.trofimov.eventnotificator.model.EventNotification;
import ru.trofimov.eventnotificator.repository.UserEventNotificationRepository;

@Service
public class EventChangeKafkaMessageHandler {

    private final EventNotificationMapper eventNotificationMapper;
    private final UserEventNotificationRepository userEventNotificationRepository;
    private final EventNotificationService eventNotificationService;

    public EventChangeKafkaMessageHandler(EventNotificationMapper eventNotificationMapper,
                                          UserEventNotificationRepository userEventNotificationRepository,
                                          EventNotificationService eventNotificationService) {
        this.eventNotificationMapper = eventNotificationMapper;
        this.userEventNotificationRepository = userEventNotificationRepository;
        this.eventNotificationService = eventNotificationService;
    }

    @Transactional
    public void handleEventChangeMessage(EventChangeKafkaMessage message) {
        EventNotification eventNotification = eventNotificationMapper.fromKafka(message);
        EventNotificationEntity eventNotificationEntity = eventNotificationMapper.toEntity(eventNotification);
        EventNotificationEntity savedEventNotification = eventNotificationService.saveNotification(eventNotificationEntity);

        message.getUserIds().forEach(userId -> {
            UserEventNotificationEntity userEntity = new UserEventNotificationEntity();
            userEntity.setUserId(userId);
            userEntity.setEventNotification(savedEventNotification);
            userEventNotificationRepository.save(userEntity);
        });
    }
}
