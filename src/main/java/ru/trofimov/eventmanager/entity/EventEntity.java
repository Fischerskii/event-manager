package ru.trofimov.eventmanager.entity;

import jakarta.persistence.*;
import ru.trofimov.eventmanager.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "max_places")
    private Integer maxPlaces;

    @Column(name = "occupied_places")
    Integer occupiedPlaces;

    @OneToMany(mappedBy = "event")
    private List<RegistrationEntity> registrationEntities;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "location_id")
    private Long locationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    public EventEntity() {
    }

    public EventEntity(
            Long id,
            String name,
            String ownerId,
            Integer maxPlaces,
            Integer occupiedPlaces,
            List<RegistrationEntity> registrationEntities,
            LocalDateTime date,
            BigDecimal cost,
            Integer duration,
            Long locationId,
            EventStatus status
    ) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.maxPlaces = maxPlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.registrationEntities = registrationEntities;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
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

    public List<RegistrationEntity> getRegistrationEntities() {
        return registrationEntities;
    }

    public void setRegistrationEntities(List<RegistrationEntity> registrationEntities) {
        this.registrationEntities = registrationEntities;
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
