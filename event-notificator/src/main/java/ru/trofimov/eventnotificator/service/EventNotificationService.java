package ru.trofimov.eventnotificator.service;

import org.springframework.stereotype.Service;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;
import ru.trofimov.eventnotificator.repository.EventNotificationRepository;

@Service
public class EventNotificationService {

    private final EventNotificationRepository eventNotificationRepository;

    public EventNotificationService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = eventNotificationRepository;
    }

    public EventNotificationEntity saveNotification(EventNotificationEntity notification) {
        return eventNotificationRepository.save(notification);
    }
}
