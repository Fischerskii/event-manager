package ru.trofimov.eventmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.entity.LocationEntity;
import ru.trofimov.eventmanager.mapper.LocationEntityMapper;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationEntityMapper locationEntityMapper) {
        this.locationRepository = locationRepository;
        this.locationEntityMapper = locationEntityMapper;
    }

    public Location createLocation(Location location) {
        if (locationRepository.existsByName(location.name())) {
            throw new IllegalArgumentException("Location name already taken");
        }

        return locationEntityMapper
                .toDomain(
                        locationRepository.save(
                                locationEntityMapper.toEntity(location)
                        )
                );
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationEntityMapper::toDomain)
                .toList();
    }

    public Location deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found location by id: %s".formatted(id));
        }

        return locationEntityMapper.toDomain(
                locationRepository.deleteByIdAndReturnLocation(id)
        );
    }

    public Location findById(Long id) {
        LocationEntity locationEntity = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found location by id: %s".formatted(id)));

        return locationEntityMapper.toDomain(locationEntity);
    }

    public Location updateLocation(Long id, Location location) {
        Location locationForUpdate = findById(id);

        if (location.capacity() < locationForUpdate.capacity()) {
            throw new IllegalArgumentException("Location capacity cannot be reduced");
        }

        return new Location(
                id,
                location.name(),
                location.address(),
                location.capacity(),
                location.description()
        );
    }
}
