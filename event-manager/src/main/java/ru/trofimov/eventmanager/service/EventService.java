package ru.trofimov.eventmanager.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.common.dto.EventChangeKafkaMessage;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.kafka.EventKafkaSender;
import ru.trofimov.eventmanager.mapper.EventEntityMapper;
import ru.trofimov.eventmanager.mapper.EventKafkaMapper;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.model.EventFilter;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.repository.EventRepository;
import ru.trofimov.eventmanager.util.AuthorizationHeaderUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final EventEntityMapper eventEntityMapper;
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final EventKafkaSender eventKafkaSender;
    private final EventKafkaMapper eventKafkaMapper;

    public EventService(EventRepository eventRepository,
                        LocationService locationService,
                        EventEntityMapper eventEntityMapper,
                        AuthorizationHeaderUtil authorizationHeaderUtil, EventKafkaSender eventKafkaSender, EventKafkaMapper eventKafkaMapper) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
        this.eventEntityMapper = eventEntityMapper;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
        this.eventKafkaSender = eventKafkaSender;
        this.eventKafkaMapper = eventKafkaMapper;
    }

    @Transactional
    public Event createEvent(Event event, String authorizationHeader) {
        Location location = locationService.findById(event.locationId());
        User creatorUser = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader);
        Long userId = creatorUser.getId();

        event = event.withUserId(userId);

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

    public Event updateEvent(Long eventId, Event eventToUpdate, String authorizationHeader) {
        EventEntity eventEntityBeforeUpdate = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id %s not found".formatted(eventId)));
        Event eventBeforeUpdate = eventEntityMapper.toDomain(eventEntityBeforeUpdate);

        Location location = locationService.findById(eventToUpdate.locationId());

        validateEventCapacity(eventToUpdate, location);


        if (eventToUpdate.maxPlaces() < eventBeforeUpdate.occupiedPlaces()) {
            throw new IllegalArgumentException("The number of available spots for " +
                    "the event cannot be reduced below the number of reserved spots.");
        }

        eventRepository.updateEvent(
                eventId,
                eventToUpdate.name(),
                eventToUpdate.maxPlaces(),
                eventToUpdate.date(),
                eventToUpdate.cost(),
                eventToUpdate.duration(),
                eventToUpdate.locationId()
        );

        EventEntity eventEntityAfterUpdate = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id %s not found".formatted(eventId)));

        Event eventAfterUpdate = eventEntityMapper.toDomain(eventEntityAfterUpdate);

        EventChangeKafkaMessage kafkaEventNewValues = eventKafkaMapper.toKafka(eventBeforeUpdate, eventAfterUpdate);
        Long userId = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader).getId();
        kafkaEventNewValues.setChangedById(userId);
        eventKafkaSender.sendEvent(kafkaEventNewValues);

        return eventAfterUpdate;
    }

    public List<Event> searchEvents(EventFilter eventFilter) {

        List<EventEntity> eventsByFilter = eventRepository.findAllByFilter(
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

    public List<Event> getAllUserCreatedEvents(String authorizationHeader) {
        User user = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader);
        Long userId = user.getId();

        List<EventEntity> currentUserEvents = eventRepository.findByOwnerId(userId);

        return currentUserEvents.stream()
                .map(eventEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    private static void validateEventCapacity(Event event, Location location) {
        if (event.maxPlaces() > location.capacity()) {
            throw new IllegalArgumentException("Event max places is greater than capacity");
        }
    }
}
