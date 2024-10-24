package ru.trofimov.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
-import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LocationDTO (

        @Size(message = "The name must contain more than 3 characters", min = 3) 
        @NotBlank(message = "Name cannot be empty") 
        String name,
        
        @NotBlank(message = "Address cannot be empty") 
        String address,
        
        @Digits(message = "Capacity should contain only numbers", integer = 6, fraction = 0)
        Integer capacity,
        
        @Size(message = "Description should not be longer than 1000 characters", max = 1000)
        String description) {
}