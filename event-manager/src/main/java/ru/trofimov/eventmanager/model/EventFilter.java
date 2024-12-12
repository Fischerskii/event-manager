package ru.trofimov.eventmanager.model;

import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventFilter(
        String name,
        Integer placesMin,
        Integer placesMax,
        LocalDateTime dateStartBefore,
        LocalDateTime dateStartAfter,
        BigDecimal costMin,
        BigDecimal costMax,
        Integer durationMin,
        Integer durationMax,
        Long locationId,
        EventStatus status
) {
}
