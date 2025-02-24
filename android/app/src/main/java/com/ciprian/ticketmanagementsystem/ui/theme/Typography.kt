package com.ciprian.ticketmanagementsystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ciprian.ticketmanagementsystem.R

val FiraMono = FontFamily(
    Font(R.font.fira_mono_regular, FontWeight.Normal),
    Font(R.font.fira_mono_bold, FontWeight.Bold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FiraMono,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)
