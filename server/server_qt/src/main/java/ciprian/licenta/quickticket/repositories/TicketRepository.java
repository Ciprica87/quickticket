package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByTicketTierId(UUID ticketTierId);
}
