package com.ciprian.ticketmanagementsystem.data.repository

import android.util.Log
import com.ciprian.ticketmanagementsystem.data.models.UserDTO
import com.ciprian.ticketmanagementsystem.data.models.ApiResponse
import com.ciprian.ticketmanagementsystem.data.local.TokenManager
import com.ciprian.ticketmanagementsystem.data.remote.api.AuthService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) {
    suspend fun loginUser(userDTO: UserDTO): Response<ApiResponse<Map<String, Any>>> {
        Log.d("UserRepository", "Attempting to login user: ${userDTO.username}")

        val response = authService.login(userDTO)

        Log.d("UserRepository", "Server response: $response")
        Log.d("UserRepository", "Server response body: ${response.body()}")

        if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d("UserRepository", "Login successful, API response: $apiResponse")

            if (apiResponse?.success == true) {
                val dataMap = apiResponse.data
                Log.d("UserRepository", "Response data: $dataMap")

                val role = dataMap?.get("role") as? String
                Log.d("UserRepository", "User role: $role")

                if (role == "ROLE_Staff") {
                    val token = dataMap["token"] as? String
                    Log.d("UserRepository", "Token received: $token")

                    val userId = dataMap?.get("userId") as? String

                    if (token != null) {
                        tokenManager.saveToken(token)
                        Log.d("UserRepository", "Token saved successfully")
                    } else {
                        Log.e("UserRepository", "Token is null or not a String")
                    }
                    if (userId != null) {
                        tokenManager.saveUserId(userId)
                        Log.d("UserRepository", "User ID saved successfully")
                    }
                } else {
                    Log.e("UserRepository", "Login failed: User is not a staff member")
                    return Response.error(
                        401,
                        response.errorBody() ?: ResponseBody.create(null, "Unauthorized: Not a staff member")
                    )
                }
            } else {
                Log.e("UserRepository", "Login failed: ${apiResponse?.message}")
            }
        } else {
            Log.e("UserRepository", "Login failed with status code: ${response.code()}")
            Log.e("UserRepository", "Error body: ${response.errorBody()?.string()}")
        }

        return response
    }

    fun logout() {
        Log.d("UserRepository", "Logging out and clearing token")
        tokenManager.clearData()
    }
}
