package ru.trofimov.eventmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank
        @Size(min = 5, message = "Size must be longer than 5 symbols")
        String login,

        @NotBlank
        @Size(min = 5, message = "Size must be longer than 5 symbols")
        String password,

        @NotNull
        @Positive(message = "Age cannot be less than 0")
        Integer age
) {
}
