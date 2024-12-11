package ru.trofimov.eventnotificator.model;

public record UserEventNotification(
        Long userId,
        EventNotification eventNotification,
        boolean isRead
) {
}
