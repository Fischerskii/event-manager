package ru.trofimov.eventmanager.dto;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
