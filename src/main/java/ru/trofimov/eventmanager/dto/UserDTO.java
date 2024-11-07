package ru.trofimov.eventmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.trofimov.eventmanager.enums.Role;

public record UserDTO(

        Long id,

        @NotBlank
        String login,

        @Positive(message = "Age cannot be less than 0")
        @NotNull
        Integer age,

        @NotNull(message = "Role cannot be null")
        Role role
) {
}
