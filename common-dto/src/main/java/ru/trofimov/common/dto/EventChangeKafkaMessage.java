package ru.trofimov.common.dto;

import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EventChangeKafkaMessage {
    private Long eventId;
    private List<Long> userIds;
    private Long ownerId;
    private Long changedById;
    private FieldChange<String> name;
    private FieldChange<Integer> maxPlaces;
    private FieldChange<LocalDateTime> date;
    private FieldChange<BigDecimal> cost;
    private FieldChange<Integer> duration;
    private FieldChange<Long> locationId;
    private FieldChange<EventStatus> status;

    public EventChangeKafkaMessage() {
    }

    public EventChangeKafkaMessage(Long eventId, List<Long> userIds, Long ownerId, Long changedById, FieldChange<String> name, FieldChange<Integer> maxPlaces, FieldChange<LocalDateTime> date, FieldChange<BigDecimal> cost, FieldChange<Integer> duration, FieldChange<Long> locationId, FieldChange<EventStatus> status) {
        this.eventId = eventId;
        this.userIds = userIds;
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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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
