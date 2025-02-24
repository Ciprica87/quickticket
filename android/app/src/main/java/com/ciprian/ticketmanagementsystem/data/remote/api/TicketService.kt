package com.ciprian.ticketmanagementsystem.data.remote.api

import com.ciprian.ticketmanagementsystem.data.models.ApiResponse
import com.ciprian.ticketmanagementsystem.data.models.TicketCheckInDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketService {
    @GET("tickets/checkin/{ticketId}/{staffId}")
    suspend fun validateTicket(
        @Path("ticketId") ticketId: String,
        @Path("staffId") staffId: String
    ): Response<ApiResponse<TicketCheckInDTO>>
}

