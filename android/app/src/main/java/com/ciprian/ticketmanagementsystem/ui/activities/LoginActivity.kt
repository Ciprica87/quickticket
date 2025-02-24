package com.ciprian.ticketmanagementsystem.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ciprian.ticketmanagementsystem.ui.screens.LoginScreen
import com.ciprian.ticketmanagementsystem.ui.theme.TicketManagementSystemTheme
import com.ciprian.ticketmanagementsystem.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketManagementSystemTheme {
                LoginScreen(
                    loginViewModel = loginViewModel,
                    onLoginSuccess = {
                        val username = loginViewModel.username.value ?: ""
                        val intent = Intent(this, ScanActivity::class.java).apply {
                            putExtra("username", username)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}


