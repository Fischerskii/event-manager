package ru.trofimov.eventmanager.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.trofimov.eventmanager.AbstractTest;
import ru.trofimov.eventmanager.dto.EventCreateRequestDTO;
import ru.trofimov.eventmanager.dto.EventDTO;
import ru.trofimov.eventmanager.entity.LocationEntity;
import ru.trofimov.common.enums.Role;
import ru.trofimov.eventmanager.repository.EventRepository;
import ru.trofimov.eventmanager.repository.LocationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class EventControllerTest extends AbstractTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldSuccessCreateEvent() throws Exception {
        LocationEntity savedLocation = createLocation();

        EventCreateRequestDTO event = new EventCreateRequestDTO(
                "event-name",
                40,
                LocalDateTime.now().plusMonths(2),
                new BigDecimal(1200),
                40,
                savedLocation.getId()
        );

        String eventJson = objectMapper.writeValueAsString(event);

        String createEventJson = mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson)
                        .header("Authorization", getAuthorizationHeader(Role.USER))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EventDTO eventDTOResponse = objectMapper.readValue(createEventJson, EventDTO.class);

        Assertions.assertNotNull(eventDTOResponse.getId());
        Assertions.assertEquals(event.getName(), eventDTOResponse.getName());
        Assertions.assertTrue(eventRepository.existsById(eventDTOResponse.getId()));
    }

    @Test
    void shouldNotCreateEventWhenRequestNotValid() throws Exception {
        LocationEntity savedLocation = createLocation();

        EventCreateRequestDTO event = new EventCreateRequestDTO(
                "some-name",
                -4,
                LocalDateTime.now().minusDays(1),
                new BigDecimal(-2),
                20,
                savedLocation.getId()
                );

        String eventJson = objectMapper.writeValueAsString(event);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventJson)
                .header("Authorization", getAuthorizationHeader(Role.USER))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSearchEventById() throws Exception {
        LocationEntity savedLocation = createLocation();

        EventCreateRequestDTO event = new EventCreateRequestDTO(
                "event-name",
                40,
                LocalDateTime.now().plusMonths(2),
                new BigDecimal("1200.00"),
                40,
                savedLocation.getId()
        );

        String eventJson = objectMapper.writeValueAsString(event);

        String createEventJson = mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson)
                        .header("Authorization", getAuthorizationHeader(Role.USER))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EventDTO eventDTO = objectMapper.readValue(createEventJson, EventDTO.class);

        Long eventId = eventDTO.getId();

        String foundEventById = mockMvc.perform(get("/events/{eventId}", eventId)
                        .header("Authorization", getAuthorizationHeader(Role.USER))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EventDTO eventById = objectMapper.readValue(foundEventById, EventDTO.class);

        assertThat(eventDTO)
                .usingRecursiveComparison()
                .isEqualTo(eventById);
    }

    private LocationEntity createLocation() {
        LocationEntity locationEntity = new LocationEntity(
                null,
                "location-name-" + UUID.randomUUID(),
                "address=" + UUID.randomUUID(),
                2000,
                "some-description"
        );

        return locationRepository.save(locationEntity);

    }
}
