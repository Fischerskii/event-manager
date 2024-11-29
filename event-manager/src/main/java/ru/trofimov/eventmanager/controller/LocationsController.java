package ru.trofimov.eventmanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventmanager.dto.LocationDTO;
import ru.trofimov.eventmanager.mapper.LocationDTOMapper;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationsController {

    private final static Logger log = LoggerFactory.getLogger(LocationsController.class);

    private final LocationService locationService;
    private final LocationDTOMapper locationDTOMapper;

    public LocationsController(LocationService locationService,
                               LocationDTOMapper locationDTOMapper) {
        this.locationService = locationService;
        this.locationDTOMapper = locationDTOMapper;
    }

    @GetMapping
    public List<LocationDTO> getLocations() {
        log.info("Get all locations");
        return locationService.getAllLocations().stream()
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
    public ResponseEntity<LocationDTO> deleteById(@PathVariable("id") Long id) {
        log.info("Delete location: {}", id);
        Location deleteLocation = locationService.deleteLocation(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(locationDTOMapper.toDto(deleteLocation));
    }

    @GetMapping("{locationId}")
    public LocationDTO getLocationById(@PathVariable("locationId") Long locationId) {
        log.info("Get location by id: {}", locationId);
        Location location = locationService.findById(locationId);
        return locationDTOMapper.toDto(location);
    }

    @PutMapping("{locationId}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable("locationId") Long locationId,
                                                      @RequestBody @Valid LocationDTO locationDTO) {
        log.info("Update location: {}", locationDTO);
        Location updatedLocation = locationService.updateLocation(locationId, locationDTOMapper.toDomain(locationDTO));

        return ResponseEntity.ok(
                locationDTOMapper
                        .toDto(updatedLocation));
    }

}
