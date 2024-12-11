package ru.trofimov.eventnotificator.entity;

import jakarta.persistence.*;
import ru.trofimov.common.dto.FieldChange;
import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event_notifications")
    public class EventNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "changed_by_id")
    private Long changedById;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "name_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "name_new_vlaue"))
    })
    private FieldChange<String> name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "max_places_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "max_places_new_vlaue"))
    })
    private FieldChange<Integer> maxPlaces;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "date_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "date_new_vlaue"))
    })
    private FieldChange<LocalDateTime> date;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "cost_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "cost_new_vlaue"))
    })
    private FieldChange<BigDecimal> cost;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "duration_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "duration_new_vlaue"))
    })
    private FieldChange<Integer> duration;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "location_id_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "location_id_new_vlaue"))
    })
    private FieldChange<Long> locationId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldValue", column = @Column(name = "status_id_old_value")),
            @AttributeOverride(name = "newValue", column = @Column(name = "status_id_new_vlaue"))
    })
    private FieldChange<EventStatus> status;

    public EventNotificationEntity() {
    }

    public EventNotificationEntity(Long id, Long eventId, Long ownerId, Long changedById, FieldChange<String> name, FieldChange<Integer> maxPlaces, FieldChange<LocalDateTime> date, FieldChange<BigDecimal> cost, FieldChange<Integer> duration, FieldChange<Long> locationId, FieldChange<EventStatus> status) {
        this.id = id;
        this.eventId = eventId;
        this.ownerId = ownerId;
        this.changedById = changedById;
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.date = date;
        this.cost = cost;
        this.duration = duration;
        this.locationId = locationId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getChangedById() {
        return changedById;
    }

    public void setChangedById(Long changedById) {
        this.changedById = changedById;
    }

    public FieldChange<String> getName() {
        return name;
    }

    public void setName(FieldChange<String> name) {
        this.name = name;
    }

    public FieldChange<Integer> getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(FieldChange<Integer> maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public FieldChange<LocalDateTime> getDate() {
        return date;
    }

    public void setDate(FieldChange<LocalDateTime> date) {
        this.date = date;
    }

    public FieldChange<BigDecimal> getCost() {
        return cost;
    }

    public void setCost(FieldChange<BigDecimal> cost) {
        this.cost = cost;
    }

    public FieldChange<Integer> getDuration() {
        return duration;
    }

    public void setDuration(FieldChange<Integer> duration) {
        this.duration = duration;
    }

    public FieldChange<Long> getLocationId() {
        return locationId;
    }

    public void setLocationId(FieldChange<Long> locationId) {
        this.locationId = locationId;
    }

    public FieldChange<EventStatus> getStatus() {
        return status;
    }

    public void setStatus(FieldChange<EventStatus> status) {
        this.status = status;
    }
}
