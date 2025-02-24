package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    List<Staff> findByAssignedEventId(UUID eventId);
}