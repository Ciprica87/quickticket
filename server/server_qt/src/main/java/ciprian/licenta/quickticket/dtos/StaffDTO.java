package ciprian.licenta.quickticket.dtos;

import java.util.UUID;

public class StaffDTO extends UserDTO {
    private UUID assignedEventId;

    public StaffDTO() {
        super();
    }

    public UUID getAssignedEventId() {
        return assignedEventId;
    }

    public void setAssignedEventId(UUID assignedEventId) {
        this.assignedEventId = assignedEventId;
    }
}
