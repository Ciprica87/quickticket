package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.EventManager;
import ciprian.licenta.quickticket.entities.UserRole;
import ciprian.licenta.quickticket.repositories.EventManagerRepository;
import ciprian.licenta.quickticket.services.validator.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class EventManagerService {
    private final EventManagerRepository eventManagerRepository;
    private final UserValidatorService userValidatorService;

    @Autowired
    public EventManagerService(EventManagerRepository eventManagerRepository, UserValidatorService userValidatorService) {
        this.eventManagerRepository = eventManagerRepository;
        this.userValidatorService = userValidatorService;
    }

    public List<EventManager> findAllEventManagers() {
        return eventManagerRepository.findAll();
    }

    public EventManager findEventManagerById(UUID id) {
        return eventManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventManager not found with ID: " + id));
    }

    @Transactional
    public EventManager createEventManager(EventManager eventManager) {
        eventManager.setRole(UserRole.EventManager);
        userValidatorService.validateUserDetails(eventManager);
        return eventManagerRepository.save(eventManager);
    }

    @Transactional
    public EventManager updateEventManager(UUID id, EventManager updatedEventManager) {
        EventManager existingEventManager = findEventManagerById(id);
        userValidatorService.validateUpdatedDetails(existingEventManager, updatedEventManager);

        existingEventManager.setUsername(updatedEventManager.getUsername());
        existingEventManager.setPassword(updatedEventManager.getPassword());
        existingEventManager.setEmail(updatedEventManager.getEmail());
        existingEventManager.setRole(UserRole.EventManager);
        return eventManagerRepository.save(existingEventManager);
    }

    public void deleteEventManager(UUID id) {
        EventManager eventManager = findEventManagerById(id);
        eventManagerRepository.delete(eventManager);
    }
}
