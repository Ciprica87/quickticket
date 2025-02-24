package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.EventManager;
import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;
import ciprian.licenta.quickticket.repositories.EventRepository;
import ciprian.licenta.quickticket.repositories.EventManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventManagerRepository eventManagerRepository;
    private final EmailService emailService;

    @Autowired
    public EventService(EventRepository eventRepository, EventManagerRepository eventManagerRepository, EmailService emailService) {
        this.eventRepository = eventRepository;
        this.eventManagerRepository = eventManagerRepository;
        this.emailService = emailService;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event findEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }

    @Transactional
    public Event createEvent(Event event) {
        // Save the event to the database
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(UUID id, Event updatedEvent) {
        Event existingEvent = findEventById(id);
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setEventManager(updatedEvent.getEventManager());
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(UUID id) {
        Event event = findEventById(id);
        eventRepository.delete(event);
    }

    public EventManager findEventManagerById(UUID id) {
        return eventManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event Manager not found with ID: " + id));
    }

    public List<Event> findEventsByEventManagerId(UUID eventManagerId) {
        return eventRepository.findByEventManagerId(eventManagerId);
    }

    public Event findEventByIdWithStaff(UUID eventId) {
        return eventRepository.findByIdWithStaffList(eventId);
    }

    @Transactional
    public void closeEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        if (event.getName().endsWith("- Closed")) {
            throw new IllegalStateException("Event is already closed.");
        }

        for (TicketTier tier : event.getTicketTiers()) {
            List<Ticket> tickets = tier.getTickets();
            for (Ticket ticket : tickets) {
                ticket.setValid(false);
            }
        }

        event.setName(event.getName() + " - Closed");

        eventRepository.save(event);
    }

    @Transactional
    public void generateTicketsAndSendEmail(UUID eventId) {
        Event event = findEventById(eventId);

        if (event.getTicketTiers().isEmpty() || event.getTicketTiers().get(0).getTickets().isEmpty()) {
            throw new IllegalStateException("No tickets available to generate and send.");
        }

        EventManager eventManager = event.getEventManager();

        if (eventManager != null && eventManager.getEmail() != null) {
            try {
                // Generate and send tickets
                emailService.sendTickets(eventManager.getEmail(), event);
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send tickets email", e);
            }
        } else {
            throw new IllegalStateException("Event Manager or email not found.");
        }
    }
}
