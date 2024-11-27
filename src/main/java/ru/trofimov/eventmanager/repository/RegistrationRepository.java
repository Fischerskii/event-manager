package ru.trofimov.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.entity.RegistrationEntity;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    @Modifying
    @Transactional
    void deleteByEventIdAndUserId(Long eventId, Long userId);


    @Query("""
                 SELECT r.event FROM RegistrationEntity r
                 WHERE r.userId = :userId
             """)
    List<EventEntity> getEventsByUserId(Long userId);
}
