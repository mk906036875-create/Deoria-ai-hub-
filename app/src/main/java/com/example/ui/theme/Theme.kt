package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = WhatsAppGreen,
    onPrimary = Color.Black,
    primaryContainer = WhatsAppGreenDark,
    onPrimaryContainer = Color.White,
    secondary = TechCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF0E7490),
    onSecondaryContainer = Color.White,
    tertiary = TechPurpleAccent,
    background = TechDarkBackground,
    onBackground = TextWhite,
    surface = TechDarkSurface,
    onSurface = TextWhite,
    surfaceVariant = TechDarkSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = TechDarkBorder
)

private val LightColorScheme = darkColorScheme(
    // Deoria AI Hub uses a dedicated premium dark AI-tech design as requested
    primary = WhatsAppGreen,
    onPrimary = Color.Black,
    primaryContainer = WhatsAppGreenDark,
    onPrimaryContainer = Color.White,
    secondary = TechCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF0E7490),
    onSecondaryContainer = Color.White,
    tertiary = TechPurpleAccent,
    background = TechDarkBackground,
    onBackground = TextWhite,
    surface = TechDarkSurface,
    onSurface = TextWhite,
    surfaceVariant = TechDarkSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = TechDarkBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
