package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.TicketCheckInDTO;
import ciprian.licenta.quickticket.dtos.TicketDTO;
import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;
import ciprian.licenta.quickticket.services.EventService;
import ciprian.licenta.quickticket.services.TicketService;
import ciprian.licenta.quickticket.services.TicketTierService;
import ciprian.licenta.quickticket.utils.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketTierService ticketTierService;

    @Autowired
    private EventService eventService;

    @PostMapping("/create/{ticketTierId}")
    public ResponseEntity<ApiResponse<UUID>> createTicket(@PathVariable UUID ticketTierId, @RequestBody TicketDTO ticketDTO) {
        TicketTier ticketTier = ticketTierService.findTicketTierById(ticketTierId);
        Ticket newTicket = TicketMapper.toEntity(ticketDTO, ticketTier);
        Ticket createdTicket = ticketService.createTicket(ticketTierId, newTicket);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket created successfully!", createdTicket.getId()));
    }

    @PutMapping("/update/{ticketId}")
    public ResponseEntity<ApiResponse<Void>> updateTicket(@PathVariable UUID ticketId, @RequestBody TicketDTO ticketDTO) {
        Ticket updatedTicket = TicketMapper.toEntity(ticketDTO, ticketTierService.findTicketTierById(ticketDTO.getTicketTierId()));
        ticketService.updateTicket(ticketId, updatedTicket);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket updated successfully!", null));
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(@PathVariable UUID ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket deleted successfully!", null));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<TicketDTO>> getTicketById(@PathVariable UUID ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        TicketDTO ticketDTO = TicketMapper.toDTO(ticket);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket retrieved successfully!", ticketDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TicketDTO>>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tickets retrieved successfully!", ticketDTOs));
    }

    @GetMapping("/validate/{ticketId}")
    public ResponseEntity<ApiResponse<Boolean>> validateTicket(@PathVariable UUID ticketId) {
        boolean isValid = ticketService.validateTicket(ticketId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket validation status retrieved successfully!", isValid));
    }


    @GetMapping("/checkin/{ticketId}/{staffId}")
    public ResponseEntity<ApiResponse<TicketCheckInDTO>> checkInTicket(
            @PathVariable UUID ticketId,
            @PathVariable UUID staffId) {

        Ticket ticket = ticketService.findTicketById(ticketId);

        if (ticket == null) {
            throw new ResourceNotFoundException("Ticket not found with ID: " + ticketId);
        }

        TicketTier ticketTier = ticket.getTicketTier();

        if (ticketTier == null) {
            throw new ResourceNotFoundException("TicketTier not found for ticket with ID: " + ticketId);
        }

        Event event = eventService.findEventByIdWithStaff(ticketTier.getEvent().getId());

        if (event == null) {
            throw new ResourceNotFoundException("Event not found for ticket tier with ID: " + ticketTier.getId());
        }

        boolean isAuthorized = event.getStaffList().stream()
                .anyMatch(staff -> staff.getId().equals(staffId));

        if (!isAuthorized) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Staff member with ID: " + staffId + " is not authorized to check in tickets for this event.");
        }

        boolean wasValid = ticket.isValid();
        if (wasValid) {
            ticket.setValid(false);
            ticketService.saveTicket(ticket);
            System.out.println("Ticket invalidated, was valid: " + wasValid + " | Now invalid: " + ticket.isValid());
        }

        TicketCheckInDTO ticketCheckInDTO = new TicketCheckInDTO();
        ticketCheckInDTO.setTicketId(ticket.getId());
        ticketCheckInDTO.setTicketTierId(ticketTier.getId());
        ticketCheckInDTO.setTicketTierName(ticketTier.getName());
        ticketCheckInDTO.setValid(wasValid);

        System.out.println("Checking in ticket: " + ticket.getId() + " | wasValid: " + ticketCheckInDTO.isValid());

        System.out.println("Sending DTO to client: " + ticketCheckInDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket checked in successfully!", ticketCheckInDTO));
    }


}
