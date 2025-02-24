    package ciprian.licenta.quickticket.entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import org.hibernate.annotations.GenericGenerator;

    import javax.persistence.*;
    import java.util.UUID;

    @Entity
    @Table(name = "tickets")
    public class Ticket {
        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        @Column(name = "id", columnDefinition = "BINARY(16)")
        private UUID id;

        @Column(name = "issued_date", nullable = false)
        private String issuedDate;

        @Column(name = "is_valid", nullable = false)
        private boolean isValid;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "tier_id")
        @JsonBackReference
        private TicketTier ticketTier;

        public Ticket() {
        }

        public Ticket(TicketTier ticketTier, String issuedDate, boolean isValid) {
            this.ticketTier = ticketTier;
            this.issuedDate = issuedDate;
            this.isValid = isValid;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getIssuedDate() {
            return issuedDate;
        }

        public void setIssuedDate(String issuedDate) {
            this.issuedDate = issuedDate;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public TicketTier getTicketTier() {
            return ticketTier;
        }

        public void setTicketTier(TicketTier ticketTier) {
            this.ticketTier = ticketTier;
        }
    }
