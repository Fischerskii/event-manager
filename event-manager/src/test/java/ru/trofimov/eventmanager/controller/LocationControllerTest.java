package ru.trofimov.eventmanager.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.trofimov.eventmanager.AbstractTest;
import ru.trofimov.eventmanager.dto.LocationDTO;
import ru.trofimov.eventmanager.enums.Role;
import ru.trofimov.eventmanager.model.Location;
import ru.trofimov.eventmanager.repository.LocationRepository;
import ru.trofimov.eventmanager.service.LocationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationControllerTest extends AbstractTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldSuccessCreateLocation() throws Exception {
        LocationDTO location = new LocationDTO(
                null,
                "some-name" + getRandomInt(),
                "some-address" + getRandomInt(),
                getRandomInt(),
                "some-description" + getRandomInt()
        );

        String locationJson = objectMapper.writeValueAsString(location);

        String createLocationJson = mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationJson)
                        .header("Authorization", getAuthorizationHeader(Role.ADMIN))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LocationDTO locationDTOResponse = objectMapper.readValue(createLocationJson, LocationDTO.class);

        Assertions.assertNotNull(locationDTOResponse.id());
        Assertions.assertEquals(location.name(), locationDTOResponse.name());
        Assertions.assertTrue(locationRepository.existsById(locationDTOResponse.id()));
    }

    @Test
    void shouldNotCreateLocationWhenRequestNotValid() throws Exception {
        LocationDTO location = new LocationDTO(
                null,
                "",
                "",
                123456789,
                "some-description"
        );

        String locationJson = objectMapper.writeValueAsString(location);

        mockMvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationJson)
                .header("Authorization", getAuthorizationHeader(Role.ADMIN))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSearchLocationById() throws Exception {
        Location location = new Location(
                null,
                "some-name",
                "some-address",
                getRandomInt(),
                "some-description"
        );

        location = locationService.createLocation(location);

        String foundLocationById = mockMvc.perform(get("/locations/{id}", location.id())
                        .header("Authorization", getAuthorizationHeader(Role.USER))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LocationDTO locationDTO = objectMapper.readValue(foundLocationById, LocationDTO.class);

        org.assertj.core.api.Assertions.assertThat(location)
                .usingRecursiveComparison()
                .isEqualTo(locationDTO);
    }

    @Test
    void shouldReturnNotFoundWhenLocationNotPresent() throws Exception {
        mockMvc.perform(get("/locations/{id}", Integer.MAX_VALUE)
                .header("Authorization", getAuthorizationHeader(Role.USER))
        )
                .andExpect(status().isNotFound());
    }
}
