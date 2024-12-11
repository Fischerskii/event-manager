package ru.trofimov.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.common.enums.EventStatus;
import ru.trofimov.eventmanager.entity.EventEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query("""
                SELECT e FROM EventEntity e
                WHERE (:name IS NULL OR e.name = :name)
                AND (:placesMin IS NULL OR e.maxPlaces >= :placesMin)
                AND (:placesMax IS NULL OR e.maxPlaces <= :placesMax)
                AND (cast(:dateStartAfter as timestamp ) IS NULL OR e.date >= :dateStartAfter)
                AND (cast(:dateStartBefore as timestamp ) IS NULL OR e.date <= :dateStartBefore)
                AND (:costMin IS NULL OR e.cost >= :costMin)
                AND (:costMax IS NULL OR e.cost <= :costMax)
                AND (:durationMin IS NULL OR e.duration >= :durationMin)
                AND (:durationMax IS NULL OR e.duration <= :durationMax)
                AND (:locationId IS NULL OR e.locationId = :locationId)
                AND (:status IS NULL OR e.status = :status)
            """)
    List<EventEntity> findAllByFilter(
            @Param("name") String name,
            @Param("placesMin") Integer placesMin,
            @Param("placesMax") Integer placesMax,
            @Param("dateStartBefore") LocalDateTime dateStartBefore,
            @Param("dateStartAfter") LocalDateTime dateStartAfter,
            @Param("costMin") BigDecimal costMin,
            @Param("costMax") BigDecimal costMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("locationId") Long locationId,
            @Param("status") EventStatus status
    );


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE EventEntity e
            SET e.name = :name,
                e.maxPlaces = :maxPlaces,
                e.date = :date,
                e.cost = :cost,
                e.duration = :duration,
                e.locationId = :locationId
            WHERE e.id = :id
            """)
    void updateEvent(Long id,
                            String name,
                            Integer maxPlaces,
                            LocalDateTime date,
                            BigDecimal cost,
                            Integer duration,
                            Long locationId);

    List<EventEntity> findByOwnerId(Long ownerId);

    List<EventEntity> findByDateBeforeAndStatus(LocalDateTime dateBefore, EventStatus status);

    @Query(value = """
                SELECT * FROM events e
                WHERE (e.date + INTERVAL '1 minute' * e.duration) <= CURRENT_TIMESTAMP AND e.status = :status
            """, nativeQuery = true)
    List<EventEntity> findExpiredEvents(String status);
}
