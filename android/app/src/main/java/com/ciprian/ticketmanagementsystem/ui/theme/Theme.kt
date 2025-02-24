package com.ciprian.ticketmanagementsystem.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = RoyalBlue,
    onPrimary = White,
    secondary = BlueGrotto,
    onSecondary = White,
    background = White,
    onBackground = NavyBlue,
    surface = BabyBlue,
    onSurface = NavyBlue,
    error = Black,
    onError = White,
)

val LightColorScheme = lightColorScheme(
    primary = RoyalBlue,
    onPrimary = White,
    secondary = BlueGrotto,
    onSecondary = White,
    background = White,
    onBackground = NavyBlue,
    surface = BabyBlue,
    onSurface = NavyBlue,
    error = Black,
    onError = White,
)

@Composable
fun TicketManagementSystemTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
