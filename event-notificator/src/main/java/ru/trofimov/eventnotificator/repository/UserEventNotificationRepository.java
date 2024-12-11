package ru.trofimov.eventnotificator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trofimov.eventnotificator.entity.UserEventNotificationEntity;

import java.util.List;

@Repository
public interface UserEventNotificationRepository extends JpaRepository<UserEventNotificationEntity, Long> {

    @Query("""
            SELECT uen FROM UserEventNotificationEntity uen
            JOIN FETCH uen.eventNotification en
            WHERE uen.userId = :userId AND uen.read = false
            """)
    List<UserEventNotificationEntity> findUnreadNotificationsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("""
            UPDATE UserEventNotificationEntity uen
            SET uen.read = true
            WHERE uen.userId = :userId
                        AND uen.eventNotification.id IN :eventIds
            """)
    void markNotificationAsRead(Long userId, List<Long> eventIds);
}
