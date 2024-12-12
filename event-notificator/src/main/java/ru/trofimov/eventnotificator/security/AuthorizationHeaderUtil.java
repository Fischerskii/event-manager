package ru.trofimov.eventnotificator.security;

import org.springframework.stereotype.Component;
import ru.trofimov.eventnotificator.security.jwt.JwtTokenManager;

@Component
public class AuthorizationHeaderUtil {
    private final JwtTokenManager jwtTokenManager;

    public AuthorizationHeaderUtil(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    public Long getUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is null or invalid");
        }

        String token = authorizationHeader.substring(7);
        return jwtTokenManager.getUserIdFromToken(token);
    }
}
