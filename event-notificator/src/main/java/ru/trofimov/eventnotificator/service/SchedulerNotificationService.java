package ru.trofimov.eventnotificator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;
import ru.trofimov.eventnotificator.repository.EventNotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerNotificationService {

    private final static Logger log = LoggerFactory.getLogger(SchedulerNotificationService.class);
    private final EventNotificationRepository eventNotificationRepository;

    @Value("${scheduling.number-of-days-accepted-as-old-notification}")
    private int countOfDaysAsOldNotification;

    public SchedulerNotificationService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = eventNotificationRepository;
    }

    @Transactional
    @Scheduled(cron = "0 */10 * * * *")
    public void deleteOldNotifications() {
        log.info("Scheduler started deleting old notifications");

        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(countOfDaysAsOldNotification);
        List<EventNotificationEntity> oldNotifications = eventNotificationRepository
                .findAllByCreatedDateTimeBefore(thresholdDate);
        int countOfDeletedNotifications = oldNotifications.size();
        eventNotificationRepository.deleteAll(oldNotifications);

        log.info("Scheduler deleted {} old notifications", countOfDeletedNotifications);
    }
}
