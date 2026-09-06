package com.vihaan.sonivo.ui.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme
import androidx.tv.material3.lightColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    tertiary = Tertiary,
    background = Background,
    onBackground = androidx.compose.ui.graphics.Color.White,
    surface = Surface,
    onSurface = androidx.compose.ui.graphics.Color.White,
)

@Composable
fun SonivoTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // For TV, we usually always stay in Dark Theme for the cinematic feel
    val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
