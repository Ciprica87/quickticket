package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.SystemAdminDTO;
import ciprian.licenta.quickticket.entities.SystemAdmin;

public class SystemAdminMapper {

    public static SystemAdmin toEntity(SystemAdminDTO dto) {
        if (dto == null) {
            return null;
        }

        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setId(dto.getId());
        systemAdmin.setUsername(dto.getUsername());
        systemAdmin.setEmail(dto.getEmail());
        systemAdmin.setPassword(dto.getPassword());
        return systemAdmin;
    }

    public static SystemAdminDTO toDTO(SystemAdmin systemAdmin) {
        if (systemAdmin == null) {
            return null;
        }

        SystemAdminDTO dto = new SystemAdminDTO();
        dto.setId(systemAdmin.getId());
        dto.setUsername(systemAdmin.getUsername());
        dto.setEmail(systemAdmin.getEmail());
        dto.setRole(systemAdmin.getRole().toString());
        return dto;
    }
}
