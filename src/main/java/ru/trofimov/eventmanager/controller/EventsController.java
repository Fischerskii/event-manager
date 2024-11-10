package ru.trofimov.eventmanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.trofimov.eventmanager.dto.EventCreateRequestDTO;
import ru.trofimov.eventmanager.dto.EventDTO;
import ru.trofimov.eventmanager.service.EventService;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    private final static Logger log = LoggerFactory.getLogger(EventsController.class);

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventCreateRequestDTO eventDTO) {
        log.info("Create event: {}", eventDTO);

        eventService.createEvent(eventDTO)

    }


}
