package ciprian.licenta.quickticket.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EventDTO {
    private UUID id;
    private String name;
    private LocalDate date;
    private String location;
    private UUID eventManagerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getEventManagerId() {
        return eventManagerId;
    }

    public void setEventManagerId(UUID eventManagerId) {
        this.eventManagerId = eventManagerId;
    }
}
