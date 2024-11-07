package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.trofimov.eventmanager.entity.UserEntity;
import ru.trofimov.eventmanager.model.User;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity userEntity);

    @Mapping(target = "password", source = "password")
    UserEntity toEntity(User user);
}