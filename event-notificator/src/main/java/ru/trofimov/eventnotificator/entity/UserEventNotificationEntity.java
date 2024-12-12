package ru.trofimov.eventnotificator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_event_notifications")
public class UserEventNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_notification_id", nullable = false)
    private EventNotificationEntity eventNotification;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    public UserEventNotificationEntity() {
    }

    public UserEventNotificationEntity(Long id, Long userId, EventNotificationEntity eventNotification, boolean read) {
        this.id = id;
        this.userId = userId;
        this.eventNotification = eventNotification;
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventNotificationEntity getEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(EventNotificationEntity eventNotification) {
        this.eventNotification = eventNotification;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
