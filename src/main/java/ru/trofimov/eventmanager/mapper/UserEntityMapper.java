package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.eventmanager.entity.UserEntity;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.service.DefaultUserParameters;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
    UserEntity toEntity(DefaultUserParameters.User userParameters);
}
