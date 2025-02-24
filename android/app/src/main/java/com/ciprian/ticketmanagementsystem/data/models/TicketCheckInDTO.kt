package com.ciprian.ticketmanagementsystem.data.models

import java.util.UUID

data class TicketCheckInDTO(
    val ticketId: UUID,
    val ticketTierId: UUID,
    val ticketTierName: String,
    val isValid: Boolean
)
