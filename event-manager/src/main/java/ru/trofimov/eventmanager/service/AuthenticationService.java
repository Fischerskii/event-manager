package ru.trofimov.eventmanager.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.dto.SignInRequest;
import ru.trofimov.common.enums.Role;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.sequrity.jwt.JwtTokenManager;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtTokenManager jwtTokenManager,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
        this.userService = userService;
    }

    public String authenticateUser(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.login(),
                        signInRequest.password()
                )
        );
        User user = userService.findByLogin(signInRequest.login());
        Role role = user.getRole();
        return jwtTokenManager.generateToken(signInRequest.login(), role);
    }
}
