package com.ciprian.ticketmanagementsystem.data.models

data class UserDTO(
    val username: String,
    val password: String,
    val email: String? = null,
    val role: String? = null
)
