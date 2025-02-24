package com.ciprian.ticketmanagementsystem.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        Log.d("TokenManager", "Saving token: $token")
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        val token = prefs.getString("jwt_token", null)
        Log.d("TokenManager", "Retrieved token: $token")
        return token
    }

    fun saveUserId(userId: String) {
        Log.d("TokenManager", "Saving userId: $userId")
        prefs.edit().putString("user_id", userId).apply()
    }

    fun getUserId(): String? {
        val userId = prefs.getString("user_id", null)
        Log.d("TokenManager", "Retrieved userId: $userId")
        return userId
    }

    fun clearData() {
        Log.d("TokenManager", "Clearing token and userId")
        prefs.edit().clear().apply()
    }
}
