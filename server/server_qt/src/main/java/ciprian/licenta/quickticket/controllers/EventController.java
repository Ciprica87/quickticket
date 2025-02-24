package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.EventDTO;
import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.EventManager;
import ciprian.licenta.quickticket.services.EventService;
import ciprian.licenta.quickticket.utils.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UUID>> createEvent(@RequestBody EventDTO eventDTO) {
        EventManager eventManager = eventService.findEventManagerById(eventDTO.getEventManagerId());
        Event newEvent = EventMapper.toEntity(eventDTO, eventManager);
        Event createdEvent = eventService.createEvent(newEvent);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event created successfully!", createdEvent.getId()));
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<ApiResponse<Void>> updateEvent(@PathVariable UUID eventId, @RequestBody EventDTO eventDTO) {
        EventManager eventManager = eventService.findEventManagerById(eventDTO.getEventManagerId());
        Event updatedEvent = EventMapper.toEntity(eventDTO, eventManager);
        eventService.updateEvent(eventId, updatedEvent);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event updated successfully!", null));
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable UUID eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event deleted successfully!", null));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDTO>> getEventById(@PathVariable UUID eventId) {
        Event event = eventService.findEventById(eventId);
        EventDTO eventDTO = EventMapper.toDTO(event);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event retrieved successfully!", eventDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        List<EventDTO> eventDTOs = events.stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Events retrieved successfully!", eventDTOs));
    }

    @GetMapping("/manager/{eventManagerId}")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getEventsByEventManager(@PathVariable UUID eventManagerId) {
        List<Event> events = eventService.findEventsByEventManagerId(eventManagerId);
        List<EventDTO> eventDTOs = events.stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Events retrieved successfully!", eventDTOs));
    }

    @PutMapping("/close/{eventId}")
    public ResponseEntity<ApiResponse<Void>> closeEvent(@PathVariable UUID eventId) {
        try {
            eventService.closeEvent(eventId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Event closed successfully!", null));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/generate-and-send-tickets/{eventId}")
    public ResponseEntity<ApiResponse<Void>> generateTicketsAndSendEmail(@PathVariable UUID eventId) {
        try {
            eventService.generateTicketsAndSendEmail(eventId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tickets generated and email sent successfully!", null));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

}
