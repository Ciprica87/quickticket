package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;
import ciprian.licenta.quickticket.repositories.TicketRepository;
import ciprian.licenta.quickticket.repositories.TicketTierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketTierRepository ticketTierRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketTierRepository ticketTierRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTierRepository = ticketTierRepository;
    }

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket findTicketById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));
    }

    @Transactional
    public Ticket createTicket(UUID ticketTierId, Ticket ticket) {
        TicketTier ticketTier = ticketTierRepository.findById(ticketTierId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketTier not found with ID: " + ticketTierId));
        ticket.setTicketTier(ticketTier);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicket(UUID id, Ticket updatedTicket) {
        Ticket existingTicket = findTicketById(id);
        existingTicket.setIssuedDate(updatedTicket.getIssuedDate());
        existingTicket.setValid(updatedTicket.isValid());
        existingTicket.setTicketTier(updatedTicket.getTicketTier());
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(UUID id) {
        Ticket ticket = findTicketById(id);
        ticketRepository.delete(ticket);
    }

    @Transactional
    public boolean validateTicket(UUID id) {
        Ticket ticket = findTicketById(id);
        if (ticket.isValid()) {
            ticket.setValid(false);
            ticketRepository.save(ticket);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Map<String, Integer> getTicketUsageByTier(UUID ticketTierId) {
        List<Ticket> tickets = ticketRepository.findByTicketTierId(ticketTierId);

        int totalTickets = tickets.size();
        int usedTickets = (int) tickets.stream().filter(ticket -> !ticket.isValid()).count();

        Map<String, Integer> ticketUsage = new HashMap<>();
        ticketUsage.put("totalTickets", totalTickets);
        ticketUsage.put("usedTickets", usedTickets);

        return ticketUsage;
    }
}

