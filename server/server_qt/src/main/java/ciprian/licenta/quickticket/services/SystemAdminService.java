package ciprian.licenta.quickticket.services;

import ciprian.licenta.quickticket.entities.SystemAdmin;
import ciprian.licenta.quickticket.entities.UserRole;
import ciprian.licenta.quickticket.repositories.SystemAdminRepository;
import ciprian.licenta.quickticket.services.validator.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class SystemAdminService {
    private final SystemAdminRepository systemAdminRepository;
    private final UserValidatorService userValidatorService;

    @Autowired
    public SystemAdminService(SystemAdminRepository systemAdminRepository, UserValidatorService userValidatorService) {
        this.systemAdminRepository = systemAdminRepository;
        this.userValidatorService = userValidatorService;
    }

    public List<SystemAdmin> findAllSystemAdmins() {
        return systemAdminRepository.findAll();
    }

    public SystemAdmin findSystemAdminById(UUID id) {
        return systemAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SystemAdmin not found with ID: " + id));
    }

    @Transactional
    public SystemAdmin createSystemAdmin(SystemAdmin systemAdmin) {
        systemAdmin.setRole(UserRole.SystemAdmin);
        userValidatorService.validateUserDetails(systemAdmin);
        return systemAdminRepository.save(systemAdmin);
    }

    @Transactional
    public SystemAdmin updateSystemAdmin(UUID id, SystemAdmin updatedSystemAdmin) {
        SystemAdmin existingSystemAdmin = findSystemAdminById(id);
        userValidatorService.validateUpdatedDetails(existingSystemAdmin, updatedSystemAdmin);

        existingSystemAdmin.setUsername(updatedSystemAdmin.getUsername());
        existingSystemAdmin.setPassword(updatedSystemAdmin.getPassword());
        existingSystemAdmin.setEmail(updatedSystemAdmin.getEmail());
        existingSystemAdmin.setRole(UserRole.SystemAdmin);
        return systemAdminRepository.save(existingSystemAdmin);
    }

    public void deleteSystemAdmin(UUID id) {
        SystemAdmin systemAdmin = findSystemAdminById(id);
        systemAdminRepository.delete(systemAdmin);
    }
}
