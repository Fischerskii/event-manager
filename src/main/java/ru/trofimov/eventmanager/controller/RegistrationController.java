package ru.trofimov.eventmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.eventmanager.dto.EventDTO;
import ru.trofimov.eventmanager.mapper.EventDtoMapper;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/events/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final static Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final EventDtoMapper eventDtoMapper;

    public RegistrationController(RegistrationService registrationService, EventDtoMapper eventDtoMapper) {
        this.registrationService = registrationService;
        this.eventDtoMapper = eventDtoMapper;
    }

    @PostMapping("{eventId}")
    public ResponseEntity<Void> registrationUserOnEvent(@PathVariable("eventId") Long eventId,
                                                        @RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Request for registration user on event {}", eventId);

        registrationService.registrationUserOnEvent(eventId, authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancel/{eventId}")
    public ResponseEntity<Void> cancelUserOnEventRegistration(@PathVariable("eventId") Long eventId,
                                                              @RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Request for cancel user on event {}", eventId);

        registrationService.cancelUserOnEventRegistration(eventId, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public List<EventDTO> getUserEvents(@RequestHeader(value = "Authorization") String authorizationHeader) {
        log.info("Request for get user events");

        List<Event> userEvents = registrationService.getUserEvents(authorizationHeader);
        return userEvents.stream()
                .map(eventDtoMapper::toDTO)
                .toList();
    }
}
