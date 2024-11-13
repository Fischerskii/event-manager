package ru.trofimov.eventmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.mapper.EventEntityMapper;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.model.EventFilter;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.repository.EventRepository;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final EventEntityMapper eventEntityMapper;

    public EventService(EventRepository eventRepository, LocationService locationService, EventEntityMapper eventEntityMapper) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
        this.eventEntityMapper = eventEntityMapper;
    }

    public Event createEvent(Event event) {
        Location location = locationService.findById(event.locationId());

        validateEventCapacity(event, location);

        EventEntity savedEvent = eventRepository.save(
                eventEntityMapper.toEntity(event)
        );
        return eventEntityMapper.toDomain(savedEvent);
    }

    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException("Event with id %s not found".formatted(eventId));
        }

        eventRepository.deleteById(eventId);
    }

    public Event findById(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id %s not found".formatted(eventId)));

        return eventEntityMapper.toDomain(eventEntity);
    }

    public Event updateEvent(Long eventId, Event eventToUpdate) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id %s not found".formatted(eventId)));
        Event currentEvent = eventEntityMapper.toDomain(eventEntity);

        Location location = locationService.findById(eventToUpdate.locationId());

        validateEventCapacity(eventToUpdate, location);


        if (eventToUpdate.maxPlaces() > currentEvent.occupiedPlaces()) {
            throw new IllegalArgumentException("The number of available spots for " +
                    "the event cannot be reduced below the number of reserved spots.");
        }

        EventEntity updatedEvent = eventRepository.updateEvent(
                eventId,
                eventToUpdate.name(),
                eventToUpdate.maxPlaces(),
                eventToUpdate.date(),
                eventToUpdate.cost(),
                eventToUpdate.duration(),
                eventToUpdate.locationId()
        );
        return eventEntityMapper.toDomain(updatedEvent);
    }

    public List<Event> searchEvents(EventFilter eventFilter) {

        List<EventEntity> eventsByFilter =  eventRepository.findAllByFilter(
                eventFilter.name(),
                eventFilter.placesMin(),
                eventFilter.placesMax(),
                eventFilter.dateStartBefore(),
                eventFilter.dateStartAfter(),
                eventFilter.costMin(),
                eventFilter.costMax(),
                eventFilter.durationMin(),
                eventFilter.durationMax(),
                eventFilter.locationId(),
                eventFilter.status()
        );

        return eventsByFilter.stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

    private static void validateEventCapacity(Event event, Location location) {
        if (event.maxPlaces() > location.capacity()) {
            throw new IllegalArgumentException("Event max places is greater than capacity");
        }
    }
}
