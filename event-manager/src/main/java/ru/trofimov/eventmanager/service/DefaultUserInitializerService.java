package ru.trofimov.eventmanager.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

        User defaultAdmin = defaultUserParameters.getAdmin();
        User defaultUser = defaultUserParameters.getUser();

        String adminEncodedPassword = passwordEncoder.encode(defaultUserParameters.getAdmin().getPassword());
        String userEncodedPassword = passwordEncoder.encode(defaultUserParameters.getUser().getPassword());

        defaultAdmin.setPassword(adminEncodedPassword);
        defaultUser.setPassword(userEncodedPassword);

        createDefaultUser(defaultAdmin);
        createDefaultUser(defaultUser);
    }

    private void createDefaultUser(User user) {
        if (!userRepository.existsByLogin(user.getLogin())) {
            userRepository.save(userEntityMapper.toEntity(user));
        }
    }
}
