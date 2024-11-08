package ru.trofimov.eventmanager.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.enums.Role;
import ru.trofimov.eventmanager.mapper.UserEntityMapper;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.repository.UserRepository;

@Service
public class DefaultUserInitializerService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final DefaultUserParameters defaultUserParameters;

    public DefaultUserInitializerService(UserRepository userRepository,
                                         UserEntityMapper userEntityMapper,
                                         PasswordEncoder passwordEncoder,
                                         DefaultUserParameters defaultUserParameters) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
        this.defaultUserParameters = defaultUserParameters;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onContextStarted() {
        createDefaultUser(
                defaultUserParameters.getAdmin().getLogin(),
                defaultUserParameters.getAdmin().getPassword(),
                defaultUserParameters.getAdmin().getAge(),
                Role.ADMIN
        );

        createDefaultUser(
                defaultUserParameters.getUser().getLogin(),
                defaultUserParameters.getUser().getPassword(),
                defaultUserParameters.getUser().getAge(),
                Role.USER
        );
    }

    private void createDefaultUser(String login, String password, int age, Role role) {
        if (!userRepository.existsByLogin(login)) {
            String encodedPassword = passwordEncoder.encode(password);

            User defaultUser = new User(
                    null,
                    login,
                    encodedPassword,
                    age,
                    role
            );

            userRepository.save(userEntityMapper.toEntity(defaultUser));
        }
    }
}
