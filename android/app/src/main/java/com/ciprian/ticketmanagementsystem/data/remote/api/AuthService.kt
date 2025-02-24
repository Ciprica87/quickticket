package com.ciprian.ticketmanagementsystem.data.remote.api

import com.ciprian.ticketmanagementsystem.data.models.ApiResponse
import com.ciprian.ticketmanagementsystem.data.models.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body userDTO: UserDTO): Response<ApiResponse<Map<String, Any>>>
}
