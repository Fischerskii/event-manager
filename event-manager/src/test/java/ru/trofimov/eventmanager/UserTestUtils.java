package ru.trofimov.eventmanager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.trofimov.eventmanager.entity.UserEntity;
import ru.trofimov.eventmanager.enums.Role;
import ru.trofimov.eventmanager.repository.UserRepository;
import ru.trofimov.eventmanager.sequrity.jwt.JwtTokenManager;

@Component
public class UserTestUtils {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    private static final String DEFAULT_ADMIN_LOGIN = "admin";
    private static final String DEFAULT_USER_LOGIN = "user";
    private static volatile boolean isUsersInitialized = false;

    public UserTestUtils(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenManager jwtTokenManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenManager = jwtTokenManager;
    }

    public String getJwtTokenWithRole(Role userRole) {
        if (!isUsersInitialized) {
            initializeTestUsers();
            isUsersInitialized = true;
        }

        return switch (userRole) {
            case USER -> jwtTokenManager.generateToken(DEFAULT_USER_LOGIN);
            case ADMIN -> jwtTokenManager.generateToken(DEFAULT_ADMIN_LOGIN);
        };
    }

    private void initializeTestUsers() {
        createUser(DEFAULT_ADMIN_LOGIN, "admin", 22, Role.ADMIN);
        createUser(DEFAULT_USER_LOGIN, "user", 25, Role.USER);
    }

    private void createUser(String login, String password, Integer age, Role role) {
        if (userRepository.existsByLogin(login)) {
            return;
        }

        String hashedPassword = passwordEncoder.encode(password);
        UserEntity userEntity = new UserEntity(null, login, hashedPassword, age, role);
        userRepository.save(userEntity);
    }
}
