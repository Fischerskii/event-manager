package ru.trofimov.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import ru.trofimov.eventmanager.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventSearchRequestDTO {

    String name;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    Integer placesMin;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    Integer placesMax;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    LocalDateTime dateStartBefore;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    LocalDateTime dateStartAfter;

    BigDecimal costMin;
    BigDecimal costMax;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    Integer durationMin;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    Integer durationMax;
    Long locationId;
    EventStatus status;

    public EventSearchRequestDTO() {
    }

    public EventSearchRequestDTO(String name,
                                 Integer placesMin,
                                 Integer placesMax,
                                 LocalDateTime dateStartBefore,
                                 LocalDateTime dateStartAfter,
                                 BigDecimal costMin,
                                 BigDecimal costMax,
                                 Integer durationMin,
                                 Integer durationMax,
                                 Long locationId,
                                 EventStatus status
    ) {
        this.name = name;
        this.placesMin = placesMin;
        this.placesMax = placesMax;
        this.dateStartBefore = dateStartBefore;
        this.dateStartAfter = dateStartAfter;
        this.costMin = costMin;
        this.costMax = costMax;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
        this.locationId = locationId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlacesMin() {
        return placesMin;
    }

    public void setPlacesMin(Integer placesMin) {
        this.placesMin = placesMin;
    }

    public Integer getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(Integer placesMax) {
        this.placesMax = placesMax;
    }

    public LocalDateTime getDateStartBefore() {
        return dateStartBefore;
    }

    public void setDateStartBefore(LocalDateTime dateStartBefore) {
        this.dateStartBefore = dateStartBefore;
    }

    public LocalDateTime getDateStartAfter() {
        return dateStartAfter;
    }

    public void setDateStartAfter(LocalDateTime dateStartAfter) {
        this.dateStartAfter = dateStartAfter;
    }

    public BigDecimal getCostMin() {
        return costMin;
    }

    public void setCostMin(BigDecimal costMin) {
        this.costMin = costMin;
    }

    public BigDecimal getCostMax() {
        return costMax;
    }

    public void setCostMax(BigDecimal costMax) {
        this.costMax = costMax;
    }

    public Integer getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(Integer durationMin) {
        this.durationMin = durationMin;
    }

    public Integer getDurationMax() {
        return durationMax;
    }

    public void setDurationMax(Integer durationMax) {
        this.durationMax = durationMax;
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
