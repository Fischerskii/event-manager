package ru.trofimov.eventmanager.model;

public record Registration (
        Long id,
        Long userId,
        Long eventId
) {
}
