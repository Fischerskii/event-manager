package ru.trofimov.eventnotificator.service;

import org.springframework.stereotype.Service;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;
import ru.trofimov.eventnotificator.repository.EventNotificationRepository;

import java.util.Optional;

@Service
public class EventNotificationService {

    private final EventNotificationRepository eventNotificationRepository;

    public EventNotificationService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = eventNotificationRepository;
    }

    public EventNotificationEntity saveNotification(EventNotificationEntity notification) {
        return eventNotificationRepository.save(notification);
    }

    public Optional<EventNotificationEntity> findByEventId(Long eventId) {
        return eventNotificationRepository.findById(eventId);
    }

    //    public List<Notification> findAllCurrentUserNotifications(String authorizationHeader) {
//        Long userId = getUserIdFromToken(authorizationHeader);
//
//        List<UserEventNotificationEntity> unreadUserNotifications = userEventNotificationRepository.findByUserIdAndIsReadFalse(userId);
//
//        return unreadUserNotifications.stream()
//                .map(userNotification -> notificationEntityMapper.toModel(userNotification.getNotification()))
//                .collect(Collectors.toList());
//    }
//
//    public void markNotificationAsRead(List<Long> notificationIds, String authorizationHeader) {
//        Long userId = getUserIdFromToken(authorizationHeader);
//
//        userEventNotificationRepository.markNotificationAsRead(notificationIds, userId);
//    }
//

}
