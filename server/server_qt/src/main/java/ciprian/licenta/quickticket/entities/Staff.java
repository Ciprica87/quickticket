package ciprian.licenta.quickticket.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event assignedEvent;

    public Staff() {
        super();
    }

    public Staff(String username, String password, String email, UserRole role) {
        super(username, password, email, role);
    }

    public Event getAssignedEvent() {
        return assignedEvent;
    }

    public void setAssignedEvent(Event assignedEvent) {
        this.assignedEvent = assignedEvent;
    }
}
