package ru.trofimov.eventmanager.dto;

import java.time.LocalDateTime;

public record ServerErrorDTO(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
