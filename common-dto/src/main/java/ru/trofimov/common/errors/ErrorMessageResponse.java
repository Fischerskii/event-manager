package ru.trofimov.common.errors;

import java.time.LocalDateTime;

public record ErrorMessageResponse(
        String message,
        String detailedMessage,
        LocalDateTime timestamp
) {
}