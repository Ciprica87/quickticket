package ciprian.licenta.quickticket.dtos;

import java.util.List;
import java.util.UUID;

public class EventManagerDTO extends UserDTO {
    private List<EventDTO> managedEvents;

    public EventManagerDTO() {
        super();
    }

    public List<EventDTO> getManagedEvents() {
        return managedEvents;
    }

    public void setManagedEvents(List<EventDTO> managedEvents) {
        this.managedEvents = managedEvents;
    }
}
