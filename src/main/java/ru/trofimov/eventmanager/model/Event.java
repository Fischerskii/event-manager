package ru.trofimov.eventmanager.model;

import ru.trofimov.eventmanager.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Event (
        Long id,
        String name,
        String ownerId,
        Integer maxPlaces,
        Integer occupiedPlaces,
        LocalDateTime date,
        BigDecimal cost,
        Integer duration,
        Long locationId,
        EventStatus status
) {
    public Event withUserId(Long userId) {
        return new Event(id,
                name,
                userId.toString(),
                maxPlaces,
                occupiedPlaces,
                date,
                cost,
                duration,
                locationId,
                status
                );
    }
}
