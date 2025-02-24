package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.Event;
import ciprian.licenta.quickticket.entities.Staff;
import ciprian.licenta.quickticket.entities.UserRole;
import ciprian.licenta.quickticket.repositories.EventRepository;
import ciprian.licenta.quickticket.repositories.StaffRepository;
import ciprian.licenta.quickticket.services.validator.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final EventRepository eventRepository;
    private final UserValidatorService userValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StaffService(StaffRepository staffRepository, EventRepository eventRepository, UserValidatorService userValidatorService) {
        this.staffRepository = staffRepository;
        this.eventRepository = eventRepository;
        this.userValidatorService = userValidatorService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Staff> findAllStaff() {
        return staffRepository.findAll();
    }

    public Staff findStaffById(UUID id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with ID: " + id));
    }

    @Transactional
    public Staff createStaff(Staff staff) {
        staff.setPassword(passwordEncoder.encode(staff.getPassword())); // Encrypt the password
        staff.setRole(UserRole.Staff);
        userValidatorService.validateUserDetails(staff);
        return staffRepository.save(staff);
    }

    @Transactional
    public Staff updateStaff(UUID id, Staff updatedStaff) {
        Staff existingStaff = findStaffById(id);
        userValidatorService.validateUpdatedDetails(existingStaff, updatedStaff);

        existingStaff.setUsername(updatedStaff.getUsername());
        if (updatedStaff.getPassword() != null && !updatedStaff.getPassword().isEmpty()) {
            existingStaff.setPassword(passwordEncoder.encode(updatedStaff.getPassword())); // Encrypt the password if updated
        }
        existingStaff.setEmail(updatedStaff.getEmail());
        existingStaff.setRole(UserRole.Staff);
        existingStaff.setAssignedEvent(updatedStaff.getAssignedEvent());
        return staffRepository.save(existingStaff);
    }

    public void deleteStaff(UUID id) {
        Staff staff = findStaffById(id);
        staffRepository.delete(staff);
    }

    @Transactional
    public Staff createStaffForEvent(UUID eventId, Staff staff) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
        staff.setAssignedEvent(event);
        staff.setPassword(passwordEncoder.encode(staff.getPassword())); // Encrypt the password
        staff.setRole(UserRole.Staff);
        userValidatorService.validateUserDetails(staff);
        return staffRepository.save(staff);
    }

    public List<Staff> findStaffByEventId(UUID eventId) {
        return staffRepository.findByAssignedEventId(eventId);
    }
}
