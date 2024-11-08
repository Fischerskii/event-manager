package ru.trofimov.eventmanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.trofimov.eventmanager.enums.Role;

public record UserDTO(

        Long id,

        @NotBlank
        String login,

        @Min(value = 18, message = "Age cannot be less than 18")
        @NotNull
        Integer age,

        @NotNull(message = "Role cannot be null")
        Role role
) {
}
