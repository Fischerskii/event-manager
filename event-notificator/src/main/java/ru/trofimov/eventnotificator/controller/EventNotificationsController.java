package ru.trofimov.eventnotificator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventnotificator.dto.EventNotificationDTO;
import ru.trofimov.eventnotificator.mapper.UserEventNotificationMapper;
import ru.trofimov.eventnotificator.model.UserEventNotification;
import ru.trofimov.eventnotificator.service.UserEventNotificationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class EventNotificationsController {

    private final static Logger log = LoggerFactory.getLogger(EventNotificationsController.class);

    private final UserEventNotificationService userEventNotificationService;
    private final UserEventNotificationMapper userEventNotificationMapper;

    public EventNotificationsController(UserEventNotificationService userEventNotificationService,
                                        UserEventNotificationMapper userEventNotificationMapper) {
        this.userEventNotificationService = userEventNotificationService;
        this.userEventNotificationMapper = userEventNotificationMapper;
    }

    @GetMapping
    public List<EventNotificationDTO> getAllUnreadNotifications(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        log.info("Getting all unread notifications");

        List<UserEventNotification> eventEventUserEventNotifications =
                userEventNotificationService.getUnreadUserNotifications(authorizationHeader);

        return eventEventUserEventNotifications.stream()
                .map(userEventNotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationAsRead(@RequestBody List<Long> notificationIds,
                                                       @RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Marking notification as read: notificationIds={}", notificationIds);

        userEventNotificationService.markNotificationAsRead(notificationIds, authorizationHeader);

        return ResponseEntity.noContent().build();
    }
}
