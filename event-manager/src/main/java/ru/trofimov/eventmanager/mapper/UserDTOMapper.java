package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.eventmanager.dto.UserDTO;
import ru.trofimov.eventmanager.model.User;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    UserDTO toDTO(User user);
    User toDomain(UserDTO userDTO);
}
