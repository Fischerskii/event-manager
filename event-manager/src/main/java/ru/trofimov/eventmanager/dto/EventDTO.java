package ru.trofimov.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import ru.trofimov.common.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventDTO {

    @Null
    Long id;

    @NotBlank
    String name;

    @NotBlank
    Long ownerId;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    @PositiveOrZero(message = "Number of seats must be a positive number")
    Integer maxPlaces;

    @PositiveOrZero(message = "Occupied places must be a positive number")
    Integer occupiedPlaces;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime date;

    @PositiveOrZero(message = "Cost cannot be a positive number")
    BigDecimal cost;

    @Min(value = 30)
    Integer duration;

    @NotNull
    Long locationId;

    @NotNull
    EventStatus status;

    public EventDTO() {
    }

    public EventDTO(Long id, String name, Long ownerId, Integer maxPlaces, Integer occupiedPlaces, LocalDateTime date, BigDecimal cost, Integer duration, Long locationId, EventStatus status) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.maxPlaces = maxPlaces;
        this.occupiedPlaces = occupiedPlaces;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(Integer maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public Integer getOccupiedPlaces() {
        return occupiedPlaces;
    }

    public void setOccupiedPlaces(Integer occupiedPlaces) {
        this.occupiedPlaces = occupiedPlaces;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
