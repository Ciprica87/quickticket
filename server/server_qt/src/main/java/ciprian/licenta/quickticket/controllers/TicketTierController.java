package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.TicketTierDTO;
import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;
import ciprian.licenta.quickticket.services.TicketService;
import ciprian.licenta.quickticket.services.TicketTierService;
import ciprian.licenta.quickticket.utils.TicketTierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticket-tiers")
public class TicketTierController {

    @Autowired
    private TicketTierService ticketTierService;

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create/{eventId}")
    public ResponseEntity<ApiResponse<UUID>> createTicketTier(@PathVariable UUID eventId, @RequestBody TicketTierDTO ticketTierDTO) {
        try {
            System.out.println("Received request to create ticket tier for event ID: " + eventId);
            TicketTier newTicketTier = TicketTierMapper.toEntity(ticketTierDTO);
            TicketTier createdTicketTier = ticketTierService.createTicketTier(eventId, newTicketTier);

            for (int i = 0; i < createdTicketTier.getAmount(); i++) {
                Ticket newTicket = new Ticket();
                newTicket.setIssuedDate(LocalDate.now().toString());
                newTicket.setValid(true);
                newTicket.setTicketTier(createdTicketTier);
                ticketService.createTicket(createdTicketTier.getId(), newTicket);
            }

            System.out.println("Created ticket tier with ID: " + createdTicketTier.getId());
            return ResponseEntity.ok(new ApiResponse<>(true, "TicketTier created successfully!", createdTicketTier.getId()));
        } catch (Exception ex) {
            System.err.println("Error creating ticket tier for event ID " + eventId + ": " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error creating ticket tier: " + ex.getMessage(), null));
        }
    }


    @PutMapping("/update/{ticketTierId}")
    public ResponseEntity<ApiResponse<Void>> updateTicketTier(@PathVariable UUID ticketTierId, @RequestBody TicketTierDTO ticketTierDTO) {
        TicketTier updatedTicketTier = TicketTierMapper.toEntity(ticketTierDTO);
        ticketTierService.updateTicketTier(ticketTierId, updatedTicketTier);
        return ResponseEntity.ok(new ApiResponse<>(true, "TicketTier updated successfully!", null));
    }

    @DeleteMapping("/delete/{ticketTierId}")
    public ResponseEntity<ApiResponse<Void>> deleteTicketTier(@PathVariable UUID ticketTierId) {
        ticketTierService.deleteTicketTier(ticketTierId);
        return ResponseEntity.ok(new ApiResponse<>(true, "TicketTier deleted successfully!", null));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<ApiResponse<List<TicketTierDTO>>> getAllTicketTiers(@PathVariable UUID eventId) {
        List<TicketTier> ticketTiers = ticketTierService.findAllTicketTiers().stream()
                .filter(ticketTier -> ticketTier.getEvent().getId().equals(eventId))
                .collect(Collectors.toList());
        List<TicketTierDTO> ticketTierDTOs = ticketTiers.stream()
                .map(TicketTierMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "TicketTiers retrieved successfully!", ticketTierDTOs));
    }

    @GetMapping("/{ticketTierId}")
    public ResponseEntity<ApiResponse<TicketTierDTO>> getTicketTierById(@PathVariable UUID ticketTierId) {
        TicketTier ticketTier = ticketTierService.findTicketTierById(ticketTierId);
        TicketTierDTO ticketTierDTO = TicketTierMapper.toDTO(ticketTier);
        return ResponseEntity.ok(new ApiResponse<>(true, "TicketTier retrieved successfully!", ticketTierDTO));
    }

    @GetMapping("/{ticketTierId}/usage")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> getTicketUsageByTier(@PathVariable UUID ticketTierId) {
        Map<String, Integer> ticketUsage = ticketService.getTicketUsageByTier(ticketTierId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket usage retrieved successfully!", ticketUsage));
    }

}
