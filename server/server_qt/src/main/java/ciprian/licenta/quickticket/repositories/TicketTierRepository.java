package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.TicketTier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketTierRepository extends JpaRepository<TicketTier, UUID> {
}
