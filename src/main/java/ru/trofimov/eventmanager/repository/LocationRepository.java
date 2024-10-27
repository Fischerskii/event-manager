package ru.trofimov.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.trofimov.eventmanager.entity.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    boolean existsByName(String name);

    @Query(value = """
            WITH deleted AS (
                DELETE FROM locations
                WHERE id = :id
                RETURNING *
            )
            SELECT * FROM deleted;
            """, nativeQuery = true)
    LocationEntity deleteByIdAndReturnLocation(Long id);
}
