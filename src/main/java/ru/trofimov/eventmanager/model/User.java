package ru.trofimov.eventmanager.model;

import ru.trofimov.eventmanager.enums.Role;

public record User(
        Long id,
        String login,
        String password,
        Integer age,
        Role role
) {
}
