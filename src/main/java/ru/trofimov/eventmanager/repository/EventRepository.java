package ru.trofimov.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventmanager.entity.EventEntity;
import ru.trofimov.eventmanager.enums.EventStatus;

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
    @Query(value = """
            WITH updated AS (
            UPDATE events
            SET name = :name,
            max_places = :maxPlaces,
            date = :date,
            cost = :cost,
            duration = :duration,
            location_id = :locationId
            WHERE id = :id
                        RETURNING *
            )
                        SELECT * FROM updated
            """, nativeQuery = true)
    EventEntity updateEvent(Long id,
                            String name,
                            Integer maxPlaces,
                            LocalDateTime date,
                            BigDecimal cost,
                            Integer duration,
                            Long locationId
    );

    List<EventEntity> findByOwnerId(String ownerId);
}
