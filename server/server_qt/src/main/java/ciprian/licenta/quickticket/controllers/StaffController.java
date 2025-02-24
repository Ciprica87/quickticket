package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.StaffDTO;
import ciprian.licenta.quickticket.entities.Staff;
import ciprian.licenta.quickticket.services.EmailService;
import ciprian.licenta.quickticket.services.StaffService;
import ciprian.licenta.quickticket.utils.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createStaff(@RequestBody StaffDTO staffDTO) {
        Staff newStaff = StaffMapper.toEntity(staffDTO);
        staffService.createStaff(newStaff);

        return ResponseEntity.ok(new ApiResponse<>(true, "Staff created successfully!", null));
    }

    @PutMapping("/update/{staffId}")
    public ResponseEntity<ApiResponse<Void>> updateStaff(@PathVariable UUID staffId, @RequestBody StaffDTO staffDTO) {
        Staff updatedStaff = StaffMapper.toEntity(staffDTO);
        staffService.updateStaff(staffId, updatedStaff);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff updated successfully!", null));
    }

    @DeleteMapping("/delete/{staffId}")
    public ResponseEntity<ApiResponse<Void>> deleteStaff(@PathVariable UUID staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff deleted successfully!", null));
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<ApiResponse<StaffDTO>> getStaffById(@PathVariable UUID staffId) {
        Staff staff = staffService.findStaffById(staffId);
        StaffDTO staffDTO = StaffMapper.toDTO(staff);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff retrieved successfully!", staffDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StaffDTO>>> getAllStaff() {
        List<Staff> staff = staffService.findAllStaff();
        List<StaffDTO> staffDTOs = staff.stream()
                .map(StaffMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff retrieved successfully!", staffDTOs));
    }

    @PostMapping("/create/{eventId}")
    public ResponseEntity<ApiResponse<Void>> createStaffForEvent(@PathVariable UUID eventId, @RequestBody StaffDTO staffDTO) {
        Staff newStaff = StaffMapper.toEntity(staffDTO);
        staffService.createStaffForEvent(eventId, newStaff);

        try {
            emailService.sendStaffCredentials(newStaff.getEmail(), newStaff.getUsername(), staffDTO.getPassword());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Staff created, but failed to send email.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Staff created successfully and email sent!", null));
    }


    @GetMapping("/event/{eventId}")
    public ResponseEntity<ApiResponse<List<StaffDTO>>> getStaffByEventId(@PathVariable UUID eventId) {
        List<Staff> staffList = staffService.findStaffByEventId(eventId);
        List<StaffDTO> staffDTOs = staffList.stream()
                .map(StaffMapper::toDTOWithPassword)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff retrieved successfully!", staffDTOs));
    }
}
