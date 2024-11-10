package ru.trofimov.eventmanager.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventCreateRequestDTO {

    @NotBlank
    String name;

    @Digits(integer = 8, fraction = 0, message = "Number of seats must be an integer")
    @PositiveOrZero(message = "Number of seats must be a positive number")
    Integer maxPlaces;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime date;

    @PositiveOrZero(message = "Cost cannot be a positive number")
    BigDecimal cost;

    @Min(value = 30)
    Integer duration;

    @NotNull
    Long locationId;
}
