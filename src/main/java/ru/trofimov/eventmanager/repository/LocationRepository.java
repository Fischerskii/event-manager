package ru.trofimov.eventmanager.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventmanager.entity.LocationEntity;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    boolean existsByName(String name);

    @Query("""
            SELECT l
            FROM LocationEntity l
            """)
    List<LocationEntity> findAllLocations(Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            UPDATE LocationEntity l
            SET l.name = :name,
            l.address = :address,
            l.capacity = :capacity,
            l.description = :description
            WHERE l.id = :id
            """)
    void updateLocation(
            Long id,
            String name,
            String address,
            Integer capacity,
            String description
    );
}
