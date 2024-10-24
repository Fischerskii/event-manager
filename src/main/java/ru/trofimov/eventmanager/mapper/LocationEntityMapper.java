package ru.trofimov.eventmanager.mapper;

import org.mapstruct.Mapper;
import ru.trofimov.eventmanager.entity.LocationEntity;
import ru.trofimov.eventmanager.model.Location;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

    Location toDomain(LocationEntity locationEntity);
    LocationEntity toEntity(Location location);
}
