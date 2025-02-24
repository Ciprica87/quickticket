package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.EventManagerDTO;
import ciprian.licenta.quickticket.entities.EventManager;
import ciprian.licenta.quickticket.services.EventManagerService;
import ciprian.licenta.quickticket.utils.EventManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event-managers")
public class EventManagerController {

    @Autowired
    private EventManagerService eventManagerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createEventManager(@RequestBody EventManagerDTO eventManagerDTO) {
        EventManager newEventManager = EventManagerMapper.toEntity(eventManagerDTO);
        eventManagerService.createEventManager(newEventManager);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event Manager created successfully!", null));
    }

    @PutMapping("/update/{eventManagerId}")
    public ResponseEntity<ApiResponse<Void>> updateEventManager(@PathVariable UUID eventManagerId, @RequestBody EventManagerDTO eventManagerDTO) {
        EventManager updatedEventManager = EventManagerMapper.toEntity(eventManagerDTO);
        eventManagerService.updateEventManager(eventManagerId, updatedEventManager);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event Manager updated successfully!", null));
    }

    @DeleteMapping("/delete/{eventManagerId}")
    public ResponseEntity<ApiResponse<Void>> deleteEventManager(@PathVariable UUID eventManagerId) {
        eventManagerService.deleteEventManager(eventManagerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event Manager deleted successfully!", null));
    }

    @GetMapping("/{eventManagerId}")
    public ResponseEntity<ApiResponse<EventManagerDTO>> getEventManagerById(@PathVariable UUID eventManagerId) {
        EventManager eventManager = eventManagerService.findEventManagerById(eventManagerId);
        EventManagerDTO eventManagerDTO = EventManagerMapper.toDTO(eventManager);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event Manager retrieved successfully!", eventManagerDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EventManagerDTO>>> getAllEventManagers() {
        List<EventManager> eventManagers = eventManagerService.findAllEventManagers();
        List<EventManagerDTO> eventManagerDTOs = eventManagers.stream()
                .map(EventManagerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Event Managers retrieved successfully!", eventManagerDTOs));
    }
}
