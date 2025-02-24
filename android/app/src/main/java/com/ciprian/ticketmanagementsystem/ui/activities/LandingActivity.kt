package com.ciprian.ticketmanagementsystem.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ciprian.ticketmanagementsystem.ui.screens.LandingScreen
import com.ciprian.ticketmanagementsystem.ui.theme.TicketManagementSystemTheme

class LandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketManagementSystemTheme {
                LandingScreen(
                    onLoginClick = {
                        startActivity(Intent(this, LoginActivity::class.java))
                    },
                    onSignUpClick = {
                        Toast.makeText(this, "Sign-up feature coming soon!", Toast.LENGTH_SHORT).show()
                    },
                    onExploreClick = {
                        startActivity(Intent(this, ScanActivity::class.java))
                    }
                )
            }
        }
    }
}
