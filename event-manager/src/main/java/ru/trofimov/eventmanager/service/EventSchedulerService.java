package ru.trofimov.eventmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.enums.EventStatus;
import ru.trofimov.eventmanager.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventSchedulerService {

    private final static Logger log = LoggerFactory.getLogger(EventSchedulerService.class);
    private final EventRepository eventRepository;

    public EventSchedulerService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedDelayString = "PT10S")
    public void schedulerCheckingEventsForStart() {
        log.info("Scheduler checking for start events");

        LocalDateTime now = LocalDateTime.now();

        List<EventEntity> eventsToStart = eventRepository
                .findByDateBeforeAndStatus(now, EventStatus.WAIT_START);

        updateEventStatus(eventsToStart, EventStatus.STARTED);
    }

    @Scheduled(fixedDelayString = "PT12S")
    public void schedulerCheckingEventsForEnd() {
        log.info("Scheduler checking for end events");

        List<EventEntity> expiredEvents = eventRepository.findExpiredEvents(EventStatus.STARTED.name());

        updateEventStatus(expiredEvents, EventStatus.FINISHED);
    }

    private void updateEventStatus(List<EventEntity> events, EventStatus newStatus) {
        if (!events.isEmpty()) {
            events.forEach(event -> {
                event.setStatus(newStatus);
            });

            eventRepository.saveAll(events);
            log.info("Updated status of {} events to {}", events.size(), newStatus.name());
        } else {
            log.info("No events to update");
        }
    }
}
