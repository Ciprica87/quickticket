package com.ciprian.ticketmanagementsystem.data.repository

import com.ciprian.ticketmanagementsystem.data.local.TokenManager
import com.ciprian.ticketmanagementsystem.data.models.ApiResponse
import com.ciprian.ticketmanagementsystem.data.models.TicketCheckInDTO
import com.ciprian.ticketmanagementsystem.data.remote.api.TicketService
import retrofit2.Response
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketService: TicketService,
    private val tokenManager: TokenManager
) {
    suspend fun validateTicket(ticketId: String): Response<ApiResponse<TicketCheckInDTO>> {
        val staffId = tokenManager.getUserId()
        if (staffId == null) {
            throw Exception("Staff ID is not available")
        }

        return ticketService.validateTicket(ticketId, staffId)
    }
}

