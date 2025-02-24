package ciprian.licenta.quickticket.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("EventManager")
public class EventManager extends User {
    @OneToMany(mappedBy = "eventManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> managedEvents;

    public EventManager() {
        super();
    }

    public EventManager(String username, String password, String email, UserRole role) {
        super(username, password, email, role);
    }

    public List<Event> getManagedEvents() {
        return managedEvents;
    }

    public void setManagedEvents(List<Event> managedEvents) {
        this.managedEvents = managedEvents;
    }
}
