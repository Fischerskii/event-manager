package ru.trofimov.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventUpdateRequestDTO {
    @NotBlank
    private String name;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    @PositiveOrZero(message = "Number of seats must be a positive number")
    private Integer maxPlaces;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @FutureOrPresent(message = "Date and time must be in the future or the present moment")
    private LocalDateTime date;

    @PositiveOrZero(message = "Cost cannot be a positive number")
    private BigDecimal cost;

    @Min(value = 30)
    private Integer duration;

    @NotNull
    private Long locationId;

    public EventUpdateRequestDTO() {
    }

    public EventUpdateRequestDTO(String name, Integer maxPlaces, LocalDateTime date, BigDecimal cost, Integer duration, Long locationId) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.date = date;
        this.cost = cost;
        this.duration = duration;
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(Integer maxPlaces) {
        this.maxPlaces = maxPlaces;
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
}
