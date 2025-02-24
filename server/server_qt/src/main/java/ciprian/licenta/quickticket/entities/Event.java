package ciprian.licenta.quickticket.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_manager_id")
    private EventManager eventManager;

    @OneToMany(mappedBy = "assignedEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketTier> ticketTiers = new ArrayList<>();

    public Event() {
    }

    public Event(String name, LocalDate date, String location) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.date = date;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<TicketTier> getTicketTiers() {
        return ticketTiers;
    }

    public void setTicketTiers(List<TicketTier> ticketTiers) {
        this.ticketTiers = ticketTiers;
    }

    public void addStaff(Staff staff) {
        staffList.add(staff);
        staff.setAssignedEvent(this);
    }

    public void removeStaff(Staff staff) {
        staffList.remove(staff);
        staff.setAssignedEvent(null);
    }

    public void addTicketTier(TicketTier ticketTier) {
        ticketTiers.add(ticketTier);
        ticketTier.setEvent(this);
    }

    public void removeTicketTier(TicketTier ticketTier) {
        ticketTiers.remove(ticketTier);
        ticketTier.setEvent(null);
    }
}
