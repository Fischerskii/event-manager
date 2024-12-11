package ru.trofimov.eventnotificator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.eventnotificator.entity.EventNotificationEntity;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotificationEntity, Long> {

}
