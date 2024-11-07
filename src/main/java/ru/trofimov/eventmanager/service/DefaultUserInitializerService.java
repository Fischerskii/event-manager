package ru.trofimov.eventmanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.enums.Role;
import ru.trofimov.eventmanager.mapper.UserEntityMapper;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.repository.UserRepository;

import java.util.List;

@Service
public class DefaultUserInitializerService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.admin.login}")
    String adminLogin;
    @Value("${default.admin.password}")
    String adminPassword;
    @Value("${default.admin.age}")
    Integer adminAge;
    @Value("${default.user.login}")
    String userLogin;
    @Value("${default.user.password}")
    String userPassword;
    @Value("${default.user.age}")
    Integer userAge;

    public DefaultUserInitializerService(UserRepository userRepository,
                                         UserEntityMapper userEntityMapper,
                                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onContextStarted() {
        if (!userRepository.existsByLogin(adminLogin) && !userRepository.existsByLogin(userLogin)) {
            String encodedAdminPassword = passwordEncoder.encode(adminPassword);
            String encodedUserPassword = passwordEncoder.encode(userPassword);

            User defaultAdmin = new User(
                    null,
                    adminLogin,
                    encodedAdminPassword,
                    adminAge,
                    Role.ADMIN
            );

            User defaultUser = new User(
                    null,
                    userLogin,
                    encodedUserPassword,
                    userAge,
                    Role.USER
            );

            userRepository.saveAll(
                    List.of(
                            userEntityMapper.toEntity(defaultUser),
                            userEntityMapper.toEntity(defaultAdmin)
                    )
            );
        }
    }
}
