package ru.trofimov.eventmanager.model;

import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Event(
        Long id,
        String name,
        Long ownerId,
        Long changedById,
        List<Long> participantIds,
        Integer maxPlaces,
        Integer occupiedPlaces,
        LocalDateTime date,
        BigDecimal cost,
        Integer duration,
        Long locationId,
        EventStatus status
) {
    public Event withUserId(Long userId) {
        return new Event(
                id,
                name,
                userId,
                changedById,
                participantIds,
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
