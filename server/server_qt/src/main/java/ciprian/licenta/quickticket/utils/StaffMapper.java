package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.StaffDTO;
import ciprian.licenta.quickticket.entities.Staff;
import ciprian.licenta.quickticket.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {

    private static EventService eventService;

    @Autowired
    public StaffMapper(EventService eventService) {
        StaffMapper.eventService = eventService;
    }

    public static Staff toEntity(StaffDTO dto) {
        if (dto == null) {
            return null;
        }

        Staff staff = new Staff();
        staff.setId(dto.getId());
        staff.setUsername(dto.getUsername());
        staff.setPassword(dto.getPassword());
        staff.setEmail(dto.getEmail());

        if (dto.getAssignedEventId() != null) {
            staff.setAssignedEvent(eventService.findEventById(dto.getAssignedEventId()));
        } else {
            staff.setAssignedEvent(null);
        }

        return staff;
    }

    public static StaffDTO toDTO(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setUsername(staff.getUsername());
        dto.setEmail(staff.getEmail());
        dto.setRole(staff.getRole().toString());
        dto.setAssignedEventId(staff.getAssignedEvent() != null ? staff.getAssignedEvent().getId() : null);

        return dto;
    }


    public static StaffDTO toDTOWithPassword(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setUsername(staff.getUsername());
        dto.setEmail(staff.getEmail());
        dto.setPassword(staff.getPassword());
        dto.setRole(staff.getRole().toString());
        dto.setAssignedEventId(staff.getAssignedEvent() != null ? staff.getAssignedEvent().getId() : null);

        return dto;
    }
}
