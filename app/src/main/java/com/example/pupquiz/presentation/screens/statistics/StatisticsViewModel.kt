package com.example.pupquiz.presentation.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.domain.model.GameStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {

    private val _statistics = MutableStateFlow(GameStatistics())
    val statistics = _statistics.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _statistics.value = prefs.getGameStats()
        }
    }

    fun cleatStats() {
        viewModelScope.launch {
            prefs.clearStats()
            _statistics.value = GameStatistics()
        }
    }
}