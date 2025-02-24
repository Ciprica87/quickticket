package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, UUID> {
}