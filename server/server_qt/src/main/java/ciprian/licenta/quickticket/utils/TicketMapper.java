package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.TicketDTO;
import ciprian.licenta.quickticket.entities.Ticket;
import ciprian.licenta.quickticket.entities.TicketTier;

public class TicketMapper {

    public static Ticket toEntity(TicketDTO dto, TicketTier ticketTier) {
        Ticket ticket = new Ticket();
        ticket.setIssuedDate(dto.getIssuedDate());
        ticket.setValid(dto.isValid());
        ticket.setTicketTier(ticketTier);
        return ticket;
    }

    public static TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTicketTierId(ticket.getTicketTier().getId());
        dto.setIssuedDate(ticket.getIssuedDate());
        dto.setValid(ticket.isValid());
        return dto;
    }
}
