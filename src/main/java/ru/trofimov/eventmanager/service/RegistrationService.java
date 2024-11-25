package ru.trofimov.eventmanager.service;

import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.enums.EventStatus;
import ru.trofimov.eventmanager.mapper.EventEntityMapper;
import ru.trofimov.eventmanager.mapper.OnEventRegistrationEntityMapper;
import ru.trofimov.eventmanager.model.Event;
import ru.trofimov.eventmanager.model.Registration;
import ru.trofimov.eventmanager.model.User;
import ru.trofimov.eventmanager.repository.RegistrationRepository;
import ru.trofimov.eventmanager.util.AuthorizationHeaderUtil;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final OnEventRegistrationEntityMapper onEventRegistrationEntityMapper;
    private final EventService eventService;
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final EventEntityMapper eventEntityMapper;

    public RegistrationService(RegistrationRepository registrationRepository,
                               OnEventRegistrationEntityMapper onEventRegistrationEntityMapper, EventService eventService, AuthorizationHeaderUtil authorizationHeaderUtil, EventEntityMapper eventEntityMapper) {
        this.registrationRepository = registrationRepository;
        this.onEventRegistrationEntityMapper = onEventRegistrationEntityMapper;
        this.eventService = eventService;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
        this.eventEntityMapper = eventEntityMapper;
    }

    public void registrationUserOnEvent(Long eventId, String authorizationHeader) {

        Event event = eventService.findById(eventId);
        if (event.status() == EventStatus.CANCELLED
                || event.status() == EventStatus.FINISHED) {
            throw new IllegalArgumentException("You cannot register for the event as it has been canceled or has already finished");
        }

        User user = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader);
        Registration registration = new Registration(
                null,
                user.getId(),
                eventId
        );

        registrationRepository.save(onEventRegistrationEntityMapper.toEntity(registration));
    }

    public void cancelUserOnEventRegistration(Long eventId, String authorizationHeader) {

        Event event = eventService.findById(eventId);
        if (event.status() == EventStatus.STARTED
        || event.status() == EventStatus.FINISHED) {
            throw new IllegalArgumentException("You cannot cancel the event as it has been started or has already finished");
        }

        User user = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader);

        registrationRepository.cancelRegistration(eventId, user.getId());
    }

    public List<Event> getUserEvents(String authorizationHeader) {
        User user = authorizationHeaderUtil.extractUserFromAuthorizationHeader(authorizationHeader);

        List<EventEntity> userEvents = registrationRepository.getEventsByUserId(user.getId());

        return userEvents.stream()
                .map(eventEntityMapper::toDomain)
                .toList();

    }
}
