package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.EventManager;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventManagerRepository extends JpaRepository<EventManager, UUID> {
}