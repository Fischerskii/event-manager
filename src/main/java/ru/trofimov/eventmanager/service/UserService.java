package ru.trofimov.eventmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.dto.SignUpRequest;
import ru.trofimov.eventmanager.entity.UserEntity;
import ru.trofimov.eventmanager.enums.Role;
import ru.trofimov.eventmanager.mapper.UserEntityMapper;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEntityMapper = userEntityMapper;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.login())) {
            throw new IllegalArgumentException("Login already exists");
        }

        String hashedPass = passwordEncoder.encode(signUpRequest.password());
        User user = new User(
                null,
                signUpRequest.login(),
                hashedPass,
                signUpRequest.age(),
                Role.USER
        );

        UserEntity savedUser = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toDomain(savedUser);
    }

    public User findByLogin(String login) {
        UserEntity user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userEntityMapper.toDomain(user);
    }

    public User findById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userEntityMapper.toDomain(userEntity);
    }
}
