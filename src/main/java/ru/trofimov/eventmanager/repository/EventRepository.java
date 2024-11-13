package ru.trofimov.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Modifying
    @Transactional
    @Query("""
            UPDATE EventEntity e
            SET e.name = :name,
            e.maxPlaces = :maxPlaces,
            e.date = :date,
            e.cost = :cost,
            e.duration = :duration,
            e.locationId = :locationId
            WHERE e.id = :id
            """)
    EventEntity updateEvent(Long id,
                            String name,
                            Integer maxPlaces,
                            LocalDateTime date,
                            BigDecimal cost,
                            Integer duration,
                            Long locationId
    );

    @Query("""
                SELECT e FROM EventEntity e
                WHERE e.name = :name
                AND (e.maxPlaces >= :minPlaces AND e.maxPlaces <= :maxPlaces)
                AND (e.date >= :minDate AND e.date <= :maxDate)
                AND (e.cost >= :minCost AND e.cost <= :maxCost)
                AND (e.duration >= :minDuration AND e.duration <= :maxDuration)
                AND e.locationId = :locationId
                AND e.status = :status
            """)
    List<EventEntity> findAllByFilter(
            String name,
            Integer placesMin,
            Integer placesMax,
            LocalDateTime dateStartBefore,
            LocalDateTime dateStartAfter,
            BigDecimal costMin,
            BigDecimal costMax,
            Integer durationMin,
            Integer durationMax,
            Integer locationId,
            EventStatus status
    );
}
