package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.TicketTier;
import ciprian.licenta.quickticket.repositories.TicketTierRepository;
import ciprian.licenta.quickticket.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TicketTierService {
    private final TicketTierRepository ticketTierRepository;
    private final EventRepository eventRepository;

    @Autowired
    public TicketTierService(TicketTierRepository ticketTierRepository, EventRepository eventRepository) {
        this.ticketTierRepository = ticketTierRepository;
        this.eventRepository = eventRepository;
    }

    public List<TicketTier> findAllTicketTiers() {
        return ticketTierRepository.findAll();
    }

    public TicketTier findTicketTierById(UUID id) {
        return ticketTierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TicketTier not found with ID: " + id));
    }

    @Transactional
    public TicketTier createTicketTier(UUID eventId, TicketTier ticketTier) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
        ticketTier.setEvent(event);
        return ticketTierRepository.save(ticketTier);
    }

    @Transactional
    public TicketTier updateTicketTier(UUID id, TicketTier updatedTicketTier) {
        TicketTier existingTicketTier = findTicketTierById(id);
        existingTicketTier.setName(updatedTicketTier.getName());
        existingTicketTier.setDescription(updatedTicketTier.getDescription());
        existingTicketTier.setAmount(updatedTicketTier.getAmount());
        return ticketTierRepository.save(existingTicketTier);
    }

    public void deleteTicketTier(UUID id) {
        TicketTier ticketTier = findTicketTierById(id);
        ticketTierRepository.delete(ticketTier);
    }
}

