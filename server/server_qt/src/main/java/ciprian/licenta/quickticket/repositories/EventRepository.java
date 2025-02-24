package ciprian.licenta.quickticket.repositories;

import ciprian.licenta.quickticket.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByEventManagerId(UUID eventManagerId);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.staffList WHERE e.id = :id")
    Event findByIdWithStaffList(UUID id);
}
