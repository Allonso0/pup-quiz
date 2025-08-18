package com.example.pupquiz.data.repository

import com.example.pupquiz.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val prefs: PreferencesManager
) : SettingsRepository {
    override fun getDarkThemeState(): Boolean {
        return prefs.isDarkThemeEnabled
    }

    override fun setDarkThemeState(enabled: Boolean) {
        prefs.isDarkThemeEnabled = enabled
    }

    override fun getSoundState(): Boolean {
        return prefs.isSoundEnabled
    }

    override fun setSoundState(enabled: Boolean) {
        prefs.isSoundEnabled = enabled
    }
}