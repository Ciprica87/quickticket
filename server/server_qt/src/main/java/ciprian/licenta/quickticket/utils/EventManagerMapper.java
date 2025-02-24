package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.EventManagerDTO;
import ciprian.licenta.quickticket.entities.EventManager;

import java.util.stream.Collectors;

public class EventManagerMapper {

    public static EventManager toEntity(EventManagerDTO dto) {
        if (dto == null) {
            return null;
        }

        EventManager eventManager = new EventManager();
        eventManager.setId(dto.getId());
        eventManager.setUsername(dto.getUsername());
        eventManager.setEmail(dto.getEmail());
        eventManager.setPassword(dto.getPassword());
        eventManager.setManagedEvents(dto.getManagedEvents() != null
                ? dto.getManagedEvents().stream().map(eventDTO -> EventMapper.toEntity(eventDTO, eventManager)).collect(Collectors.toList())
                : null);
        return eventManager;
    }

    public static EventManagerDTO toDTO(EventManager eventManager) {
        if (eventManager == null) {
            return null;
        }

        EventManagerDTO dto = new EventManagerDTO();
        dto.setId(eventManager.getId());
        dto.setUsername(eventManager.getUsername());
        dto.setEmail(eventManager.getEmail());
        dto.setRole(eventManager.getRole().toString());
        dto.setManagedEvents(eventManager.getManagedEvents() != null
                ? eventManager.getManagedEvents().stream().map(EventMapper::toDTO).collect(Collectors.toList())
                : null);
        return dto;
    }
}
