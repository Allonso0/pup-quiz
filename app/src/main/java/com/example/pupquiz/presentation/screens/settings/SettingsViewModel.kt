package com.example.pupquiz.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.domain.usecase.GetDarkThemeState
import com.example.pupquiz.domain.usecase.PlaySwitchSound
import com.example.pupquiz.domain.usecase.SetDarkThemeState
import com.example.pupquiz.domain.usecase.SetSoundEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val getDarkThemeState: GetDarkThemeState,
    private val setDarkThemeState: SetDarkThemeState,
    private val prefsManager: PreferencesManager,
    private val playSwitchSound: PlaySwitchSound,
    private val setSoundEnabled: SetSoundEnabled
) : ViewModel() {

    val isDarkThemeEnabled = prefsManager.themeUpdates
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = getDarkThemeState()
        )

    val isSoundEnabled = prefsManager.soundUpdates
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = prefsManager.isSoundEnabled
        )

    fun toggleDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeState(enabled)
            playSwitchSound()
        }
    }

    fun toggleSound(enabled: Boolean) {
        viewModelScope.launch {
            setSoundEnabled(enabled)
            playSwitchSound()
        }
    }
}