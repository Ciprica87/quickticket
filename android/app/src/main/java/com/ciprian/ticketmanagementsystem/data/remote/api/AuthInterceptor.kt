package com.ciprian.ticketmanagementsystem.data.remote.api

import android.util.Log
import com.ciprian.ticketmanagementsystem.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        val modifiedRequest = if (originalRequest.url().toString().contains("/auth/login")) {
            Log.d("AuthInterceptor", "Login request detected, no token added")
            originalRequest
        } else {
            Log.d("AuthInterceptor", "Token retrieved: $token")
            originalRequest.newBuilder().apply {
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                    Log.d("AuthInterceptor", "Authorization header added: Bearer $it")
                }
            }.build()
        }

        Log.d("AuthInterceptor", "Request URL: ${modifiedRequest.url()}")

        val response = chain.proceed(modifiedRequest)
        Log.d("AuthInterceptor", "Response Code: ${response.code()}")

        return response
    }
}

