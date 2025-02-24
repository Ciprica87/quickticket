package ciprian.licenta.quickticket.dtos;

import java.util.UUID;

public class TicketDTO {
    private UUID id;
    private UUID ticketTierId;
    private String issuedDate;
    private boolean isValid;

    public TicketDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTicketTierId() {
        return ticketTierId;
    }

    public void setTicketTierId(UUID ticketTierId) {
        this.ticketTierId = ticketTierId;
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
}
