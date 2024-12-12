package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.eventmanager.dto.LocationDTO;
import ru.trofimov.eventmanager.model.Location;

@Mapper(componentModel = "spring")
public interface LocationDTOMapper {

    Location toDomain(LocationDTO locationDTO);
    LocationDTO toDto(Location location);
}
