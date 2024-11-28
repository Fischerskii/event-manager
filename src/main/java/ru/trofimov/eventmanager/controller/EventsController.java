package ru.trofimov.eventmanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventmanager.dto.EventCreateRequestDTO;
import ru.trofimov.eventmanager.dto.EventDTO;
import ru.trofimov.eventmanager.dto.EventSearchRequestDTO;
import ru.trofimov.eventmanager.dto.EventUpdateRequestDTO;
import ru.trofimov.eventmanager.mapper.EventDtoMapper;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;
    private final EventDtoMapper eventDtoMapper;

    private final static Logger log = LoggerFactory.getLogger(EventsController.class);

    public EventsController(EventService eventService, EventDtoMapper eventDtoMapper) {
        this.eventService = eventService;
        this.eventDtoMapper = eventDtoMapper;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventCreateRequestDTO eventDTO,
                                                @RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Create event: {}", eventDTO);

        Event event = eventService.createEvent(eventDtoMapper.toDomain(eventDTO), authorizationHeader);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventDtoMapper.toDTO(event));
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long eventId) {
        log.info("Delete event: {}", eventId);

        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{eventId}")
    public EventDTO getEventById(@PathVariable("eventId") Long eventId) {
        log.info("Get event: {}", eventId);

        Event event = eventService.findById(eventId);
        return eventDtoMapper.toDTO(event);
    }

    @PutMapping("{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId,
                                                @Valid @RequestBody EventUpdateRequestDTO eventDTO) {
        log.info("Update event: {}", eventDTO);

        Event updatedEvent = eventService.updateEvent(
                eventId,
                eventDtoMapper.toDomain(eventDTO)
        );

        return ResponseEntity.ok(eventDtoMapper.toDTO(updatedEvent));
    }

    @PostMapping("/search")
    public List<EventDTO> searchEvents(@RequestBody @Valid EventSearchRequestDTO eventSearchRequestDTO) {
        log.info("Search events: {}", eventSearchRequestDTO);

        List<Event> eventsFoundByFilter = eventService.searchEvents(
                eventDtoMapper.toDomain(eventSearchRequestDTO)
        );

        return eventsFoundByFilter.stream()
                .map(eventDtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/my")
    public List<EventDTO> getAllCurrentUserEvents(@RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Get all current user events");

        List<Event> userEvents = eventService.getAllUserCreatedEvents(authorizationHeader);

        return userEvents.stream()
                .map(eventDtoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
