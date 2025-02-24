package ciprian.licenta.quickticket.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TicketCheckInDTO {
    private UUID ticketId;
    private UUID ticketTierId;
    private String ticketTierName;

    @JsonProperty("isValid")
    private boolean isValid;

    public TicketCheckInDTO() {
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getTicketTierId() {
        return ticketTierId;
    }

    public void setTicketTierId(UUID ticketTierId) {
        this.ticketTierId = ticketTierId;
    }

    public String getTicketTierName() {
        return ticketTierName;
    }

    public void setTicketTierName(String ticketTierName) {
        this.ticketTierName = ticketTierName;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
