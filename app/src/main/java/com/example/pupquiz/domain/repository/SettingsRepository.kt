package com.example.pupquiz.domain.repository

interface SettingsRepository {
    fun getDarkThemeState(): Boolean
    fun setDarkThemeState(enabled: Boolean)

    fun getSoundState(): Boolean
    fun setSoundState(enabled: Boolean)
}