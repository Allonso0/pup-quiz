package com.example.pupquiz.data.repository

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class PreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )

    private val _themeUpdates = MutableSharedFlow<Boolean>(replay = 1)
    val themeUpdates: SharedFlow<Boolean> = _themeUpdates

    var isDarkThemeEnabled: Boolean
        get() = sharedPreferences.getBoolean("dark_theme", false)
        set(value) {
            sharedPreferences.edit { putBoolean("dark_theme", value) }
            _themeUpdates.tryEmit(value)
        }

    var isSoundEnabled: Boolean
        get() = sharedPreferences.getBoolean("sound", true)
        set(value) = sharedPreferences.edit { putBoolean("sound", value) }
}