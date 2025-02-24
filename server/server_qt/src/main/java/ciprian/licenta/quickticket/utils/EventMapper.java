package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.EventDTO;
import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.EventManager;

public class EventMapper {

    public static Event toEntity(EventDTO dto, EventManager eventManager) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setEventManager(eventManager);
        return event;
    }

    public static EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDate(event.getDate());
        dto.setLocation(event.getLocation());
        dto.setEventManagerId(event.getEventManager() != null ? event.getEventManager().getId() : null);
        return dto;
    }
}
