package com.example.pupquiz.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.domain.usecase.GetDarkThemeState
import com.example.pupquiz.domain.usecase.GetSoundState
import com.example.pupquiz.domain.usecase.SetDarkThemeState
import com.example.pupquiz.domain.usecase.SetSoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val getDarkThemeState: GetDarkThemeState,
    private val setDarkThemeState: SetDarkThemeState,
    private val getSoundState: GetSoundState,
    private val setSoundState: SetSoundState,
    private val prefsManager: PreferencesManager
) : ViewModel() {

    val isDarkThemeEnabled = prefsManager.themeUpdates
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = getDarkThemeState()
        )

    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled = _isSoundEnabled.asStateFlow()

//    init {
//        viewModelScope.launch {
//            _isDarkThemeEnabled.value = getDarkThemeState()
//            _isSoundEnabled.value = getSoundState()
//        }
//    }

    fun toggleDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeState(enabled)
        }
    }

    fun toggleSound(enabled: Boolean) {
        _isSoundEnabled.value = enabled
        viewModelScope.launch {
            setSoundState(enabled)
        }
    }
}