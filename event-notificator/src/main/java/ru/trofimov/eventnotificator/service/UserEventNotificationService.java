package ru.trofimov.eventnotificator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventnotificator.entity.UserEventNotificationEntity;
import ru.trofimov.eventnotificator.mapper.UserEventNotificationMapper;
import ru.trofimov.eventnotificator.model.UserEventNotification;
import ru.trofimov.eventnotificator.repository.UserEventNotificationRepository;
import ru.trofimov.eventnotificator.security.jwt.JwtTokenManager;

import java.util.List;

@Service
public class UserEventNotificationService {

    private final JwtTokenManager jwtTokenManager;
    private final UserEventNotificationRepository userEventNotificationRepository;
    private final UserEventNotificationMapper userEventNotificationMapper;

    public UserEventNotificationService(JwtTokenManager jwtTokenManager,
                                        UserEventNotificationRepository userEventNotificationRepository,
                                        UserEventNotificationMapper userEventNotificationMapper) {
        this.jwtTokenManager = jwtTokenManager;
        this.userEventNotificationRepository = userEventNotificationRepository;
        this.userEventNotificationMapper = userEventNotificationMapper;
    }

//    public void createUserNotifications(Long eventId, List<Long> userIds) {
//        EventNotificationEntity eventNotification = eventNotificationService.findByEventId(eventId)
//                .orElseThrow(() -> new IllegalArgumentException("Event notification not found for eventId: " + eventId));
//
//        List<UserEventNotificationEntity> userNotifications = userIds.stream()
//                .map(userId -> new UserEventNotificationEntity(null, userId, eventNotification, false, null))
//                .toList();
//
//        userEventNotificationRepository.saveAll(userNotifications);
//    }

    public List<UserEventNotification> getUnreadUserNotifications(String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader);
        List<UserEventNotificationEntity> userEventNotificationEntities =
                userEventNotificationRepository.findUnreadNotificationsByUserId(userId);
        return userEventNotificationEntities.stream()
                .map(userEventNotificationMapper::toDomain)
                .toList();
    }

    @Transactional
    public void markNotificationAsRead(List<Long> eventIds, String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader);
        userEventNotificationRepository.markNotificationAsRead(userId, eventIds);
    }

    private Long getUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is null or invalid");
        }

        String token = authorizationHeader.substring(7);
        return jwtTokenManager.getUserIdFromToken(token);
    }
}
