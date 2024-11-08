package ru.trofimov.eventmanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventmanager.dto.JwtTokenResponseDTO;
import ru.trofimov.eventmanager.dto.SignInRequest;
import ru.trofimov.eventmanager.dto.SignUpRequest;
import ru.trofimov.eventmanager.dto.UserDTO;
import ru.trofimov.eventmanager.mapper.UserDTOMapper;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.service.AuthenticationService;
import ru.trofimov.eventmanager.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserDTOMapper userDTOMapper;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService,
                          AuthenticationService authenticationService,
                          UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(
            @RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("Get request for sign-up: login={}", signUpRequest.login());
        User user = userService.registerUser(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDTOMapper.toDTO(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtTokenResponseDTO> authenticateUser(
            @RequestBody @Valid SignInRequest signInRequest) {
        log.info("Get request for sign-in: login={}", signInRequest.login());

        String token = authenticationService.authenticateUser(signInRequest);

        return ResponseEntity.ok(new JwtTokenResponseDTO(token));
    }

    @GetMapping("{id}")
    public UserDTO findById(@PathVariable Long id) {
        log.info("Get request for user with id: {}", id);

        User user = userService.findById(id);

        return userDTOMapper.toDTO(user);
    }
}
