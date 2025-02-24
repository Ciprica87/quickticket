package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.services.EmailService;
import ciprian.licenta.quickticket.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.UUID;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EventService eventService;

    @PostMapping("/send-tickets")
    public void sendTickets(@RequestParam String email, @RequestParam UUID eventId) throws MessagingException {
        Event event = eventService.findEventById(eventId);
        emailService.sendTickets(email, event);
    }
}
