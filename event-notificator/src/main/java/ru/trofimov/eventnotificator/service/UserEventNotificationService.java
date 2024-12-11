package ru.trofimov.eventnotificator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventnotificator.entity.UserEventNotificationEntity;
import ru.trofimov.eventnotificator.mapper.UserEventNotificationMapper;
import ru.trofimov.eventnotificator.model.UserEventNotification;
import ru.trofimov.eventnotificator.repository.UserEventNotificationRepository;
import ru.trofimov.eventnotificator.security.AuthorizationHeaderUtil;

import java.util.List;

@Service
public class UserEventNotificationService {

    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final UserEventNotificationRepository userEventNotificationRepository;
    private final UserEventNotificationMapper userEventNotificationMapper;

    public UserEventNotificationService(AuthorizationHeaderUtil authorizationHeaderUtil,
                                        UserEventNotificationRepository userEventNotificationRepository,
                                        UserEventNotificationMapper userEventNotificationMapper) {
        this.authorizationHeaderUtil = authorizationHeaderUtil;
        this.userEventNotificationRepository = userEventNotificationRepository;
        this.userEventNotificationMapper = userEventNotificationMapper;
    }

    public List<UserEventNotification> getUnreadUserNotifications(String authorizationHeader) {
        Long userId = authorizationHeaderUtil.getUserIdFromToken(authorizationHeader);
        List<UserEventNotificationEntity> userEventNotificationEntities =
                userEventNotificationRepository.findUnreadNotificationsByUserId(userId);
        return userEventNotificationEntities.stream()
                .map(userEventNotificationMapper::toDomain)
                .toList();
    }

    @Transactional
    public void markNotificationAsRead(List<Long> eventIds, String authorizationHeader) {
        Long userId = authorizationHeaderUtil.getUserIdFromToken(authorizationHeader);
        userEventNotificationRepository.markNotificationAsRead(userId, eventIds);
    }
}
