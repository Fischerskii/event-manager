package ru.trofimov.eventmanager.dto;

import jakarta.validation.constraints.*;

public record SignUpRequest(

        @NotBlank
        @Size(min = 5, message = "Size must be longer than 5 symbols")
        String login,

        @NotBlank
        @Size(min = 5, message = "Size must be longer than 5 symbols")
        String password,

        @NotNull
        @Min(value = 18, message = "Age cannot be less than 18")
        Integer age
) {
}
