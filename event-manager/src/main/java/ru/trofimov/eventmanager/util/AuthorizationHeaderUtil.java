package ru.trofimov.eventmanager.util;

import org.springframework.stereotype.Component;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.sequrity.jwt.JwtTokenManager;
import ru.trofimov.eventmanager.service.UserService;

@Component
public class AuthorizationHeaderUtil {
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public AuthorizationHeaderUtil(JwtTokenManager jwtTokenManager, UserService userService) {
        this.jwtTokenManager = jwtTokenManager;
        this.userService = userService;
    }

    public User extractUserFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is null or invalid");
        }

        String token = authorizationHeader.substring(7);
        String login = jwtTokenManager.getLoginFromToken(token);
        return userService.findByLogin(login);
    }
}
