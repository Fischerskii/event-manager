package ru.trofimov.eventmanager.dto;

import ru.trofimov.eventmanager.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventSearchRequestDTO {

    String name;
    Integer placesMin;
    Integer placesMax;
    LocalDateTime dateStartBefore;
    LocalDateTime dateStartAfter;
    BigDecimal costMin;
    BigDecimal costMax;
    Integer durationMin;
    Integer durationMax;
    Integer locationId;
    EventStatus status;
}
