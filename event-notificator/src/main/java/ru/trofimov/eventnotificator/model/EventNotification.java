package ru.trofimov.eventnotificator.model;

import ru.trofimov.common.dto.FieldChange;
import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventNotification(
        Long eventId,
        Long ownerId,
        Long changedById,
        FieldChange<String> name,
        FieldChange<Integer> maxPlaces,
        FieldChange<LocalDateTime> date,
        FieldChange<BigDecimal> cost,
        FieldChange<Integer> duration,
        FieldChange<Long> locationId,
        FieldChange<EventStatus> status
) {
}
