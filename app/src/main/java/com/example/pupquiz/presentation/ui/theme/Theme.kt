package com.example.pupquiz.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

data class CustomColors(
    val textColor: Color,
    val secondTextColor: Color,
    val thirdTextColor: Color,
    val iconColor: Color,
    val background: Color,
    val secondBackground: Color,
    val switchInactive: Color,
    val switchActive: Color,
    val switchCircle: Color
)

val LightCustomColors = CustomColors(
    textColor = LT_textColor,
    secondTextColor = LT_secondTextColor,
    thirdTextColor = LT_thirdTextColor,
    iconColor = LT_iconColor,
    background = LT_background,
    secondBackground = LT_secondBackground,
    switchInactive = LT_switch_inactive,
    switchActive = LT_switch_active,
    switchCircle = LT_switch_circle
)

val DarkCustomColors = CustomColors(
    textColor = DT_textColor,
    secondTextColor = DT_secondTextColor,
    thirdTextColor = DT_thirdTextColor,
    iconColor = DT_iconColor,
    background = DT_background,
    secondBackground = DT_secondBackground,
    switchInactive = DT_switch_inactive,
    switchActive = DT_switch_active,
    switchCircle = DT_switch_circle
)

val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

@Composable
fun PupQuizTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors
    val materialColors = if (darkTheme) darkColorScheme() else lightColorScheme()

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = Typography,
            content = content
        )
    }
}