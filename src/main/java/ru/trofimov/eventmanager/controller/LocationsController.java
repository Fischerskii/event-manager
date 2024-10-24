package ru.trofimov.eventmanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventmanager.mapper.LocationDTOMapper;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.service.LocationService;
import ru.trofimov.eventmanager.dto.LocationDTO;
import ru.trofimov.eventmanager.validation.LocationSearchFilter;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationsController {

    private final static Logger log = LoggerFactory.getLogger(LocationsController.class);

    private final LocationService locationService;
    private final LocationDTOMapper locationDTOMapper;

    @Autowired
    public LocationsController(LocationService locationService,
                               LocationDTOMapper locationDTOMapper) {
        this.locationService = locationService;
        this.locationDTOMapper = locationDTOMapper;
    }

    @GetMapping
    public List<LocationDTO> getLocations(@Valid LocationSearchFilter locationSearchFilter) {
        log.info("Get all locations");
        return locationService.getAllLocations(locationSearchFilter).stream()
                .map(locationDTOMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        log.info("Post request for create location: {}", locationDTO);

        Location location = locationService.createLocation(locationDTOMapper.toDomain(locationDTO));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationDTOMapper.toDto(location));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Delete location: {}", id);
        locationService.deleteLocation(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public LocationDTO getLocationById(@PathVariable("id") Long id) {
        log.info("Get location by id: {}", id);
        Location location = locationService.findById(id);
        return locationDTOMapper.toDto(location);
    }

    @PutMapping("{id}")
    public LocationDTO updateLocation(@PathVariable("id") Long id,
                                      @RequestBody @Valid LocationDTO locationDTO) {
        log.info("Update location: {}", locationDTO);
        Location updatedLocation = locationService.updateLocation(id, locationDTOMapper.toDomain(locationDTO));

        return locationDTOMapper.toDto(updatedLocation);
    }

}
