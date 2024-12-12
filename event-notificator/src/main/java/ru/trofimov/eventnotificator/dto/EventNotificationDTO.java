package ru.trofimov.eventnotificator.dto;

import jakarta.validation.constraints.NotNull;
import ru.trofimov.common.dto.FieldChange;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventNotificationDTO(

        @NotNull
        Long eventId,
        FieldChange<String> name,
        FieldChange<LocalDateTime> date,
        FieldChange<BigDecimal> cost,
        FieldChange<Integer> duration,
        FieldChange<Long> locationId
) {
}
