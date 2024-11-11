package ru.trofimov.eventmanager.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.trofimov.eventmanager.model.User;

@Component
@ConfigurationProperties(prefix = "default")
public class DefaultUserParameters {

    private final User user = new User();
    private final User admin = new User();

    public User getUser() {
        return user;
    }

    public User getAdmin() {
        return admin;
    }
}
