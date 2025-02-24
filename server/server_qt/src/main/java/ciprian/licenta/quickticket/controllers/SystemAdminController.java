package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.SystemAdminDTO;
import ciprian.licenta.quickticket.entities.SystemAdmin;
import ciprian.licenta.quickticket.services.SystemAdminService;
import ciprian.licenta.quickticket.utils.SystemAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system-admins")
public class SystemAdminController {

    @Autowired
    private SystemAdminService systemAdminService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createSystemAdmin(@RequestBody SystemAdminDTO systemAdminDTO) {
        SystemAdmin newSystemAdmin = SystemAdminMapper.toEntity(systemAdminDTO);
        systemAdminService.createSystemAdmin(newSystemAdmin);
        return ResponseEntity.ok(new ApiResponse<>(true, "System Admin created successfully!", null));
    }

    @PutMapping("/update/{systemAdminId}")
    public ResponseEntity<ApiResponse<Void>> updateSystemAdmin(@PathVariable UUID systemAdminId, @RequestBody SystemAdminDTO systemAdminDTO) {
        SystemAdmin updatedSystemAdmin = SystemAdminMapper.toEntity(systemAdminDTO);
        systemAdminService.updateSystemAdmin(systemAdminId, updatedSystemAdmin);
        return ResponseEntity.ok(new ApiResponse<>(true, "System Admin updated successfully!", null));
    }

    @DeleteMapping("/delete/{systemAdminId}")
    public ResponseEntity<ApiResponse<Void>> deleteSystemAdmin(@PathVariable UUID systemAdminId) {
        systemAdminService.deleteSystemAdmin(systemAdminId);
        return ResponseEntity.ok(new ApiResponse<>(true, "System Admin deleted successfully!", null));
    }

    @GetMapping("/{systemAdminId}")
    public ResponseEntity<ApiResponse<SystemAdminDTO>> getSystemAdminById(@PathVariable UUID systemAdminId) {
        SystemAdmin systemAdmin = systemAdminService.findSystemAdminById(systemAdminId);
        SystemAdminDTO systemAdminDTO = SystemAdminMapper.toDTO(systemAdmin);
        return ResponseEntity.ok(new ApiResponse<>(true, "System Admin retrieved successfully!", systemAdminDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SystemAdminDTO>>> getAllSystemAdmins() {
        List<SystemAdmin> systemAdmins = systemAdminService.findAllSystemAdmins();
        List<SystemAdminDTO> systemAdminDTOs = systemAdmins.stream()
                .map(SystemAdminMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "System Admins retrieved successfully!", systemAdminDTOs));
    }
}
