package ru.trofimov.eventmanager.validation;

import jakarta.validation.constraints.Min;

public record LocationSearchFilter(
        Long locationId,

        @Min(0)
        Integer pageNumber,

        @Min(5)
        Integer pageSize
) {
}
