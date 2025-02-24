package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.TicketTierDTO;
import ciprian.licenta.quickticket.entities.TicketTier;

public class TicketTierMapper {

    public static TicketTierDTO toDTO(TicketTier ticketTier) {
        return new TicketTierDTO(
                ticketTier.getId(),
                ticketTier.getName(),
                ticketTier.getDescription(),
                ticketTier.getAmount()
        );
    }

    public static TicketTier toEntity(TicketTierDTO ticketTierDTO) {
        TicketTier ticketTier = new TicketTier();
        ticketTier.setName(ticketTierDTO.getName());
        ticketTier.setDescription(ticketTierDTO.getDescription());
        ticketTier.setAmount(ticketTierDTO.getAmount());
        return ticketTier;
    }
}
