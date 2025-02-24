    package com.ciprian.ticketmanagementsystem.ui.activities

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import com.ciprian.ticketmanagementsystem.data.local.TokenManager
    import com.ciprian.ticketmanagementsystem.ui.screens.ScanScreen
    import com.ciprian.ticketmanagementsystem.ui.theme.TicketManagementSystemTheme
    import com.ciprian.ticketmanagementsystem.ui.viewmodels.TicketCheckInViewModel
    import dagger.hilt.android.AndroidEntryPoint
    import kotlinx.coroutines.flow.collect
    import javax.inject.Inject

    @AndroidEntryPoint
    class ScanActivity : ComponentActivity() {

        @Inject
        lateinit var tokenManager: TokenManager

        private val ticketCheckInViewModel: TicketCheckInViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val username = intent.getStringExtra("username") ?: ""

            setContent {
                TicketManagementSystemTheme {
                    ScanScreen(
                        username = username,
                        ticketCheckInViewModel = ticketCheckInViewModel,
                        onLogout = {
                            logout()
                        }
                    )
                }
            }
        }

        private fun logout() {
            tokenManager.clearData()
            navigateToLandingScreen()
        }

        private fun navigateToLandingScreen() {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
