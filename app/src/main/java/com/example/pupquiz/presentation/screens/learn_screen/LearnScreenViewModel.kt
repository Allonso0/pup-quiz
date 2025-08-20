package com.example.pupquiz.presentation.screens.learn_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.domain.usecase.CheckConnectivity
import com.example.pupquiz.domain.usecase.GetAllBreeds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnScreenViewModel @Inject constructor(
    private val getAllBreeds: GetAllBreeds,
    private val checkConnectivity: CheckConnectivity
) : ViewModel() {

    private val _uiState = MutableStateFlow<LearnScreenUiState>(LearnScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadBreeds() {
        viewModelScope.launch {
            _uiState.value = LearnScreenUiState.Loading

            try {
                val isOnline = checkConnectivity()
                val breeds = getAllBreeds()

                if (breeds.isEmpty()) {
                    _uiState.value = LearnScreenUiState.Empty(
                        isOnline = isOnline,
                        message =
                            if (isOnline) {
                                "No breeds found"
                            } else {
                                "No data available offline"
                            }

                    )
                } else {
                    _uiState.value = LearnScreenUiState.Success(
                        breeds = breeds,
                        isOnline = isOnline
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LearnScreenUiState.Error(
                    message = "Failed to load breeds: ${e.message}",
                    isOnline = checkConnectivity()
                )
            }
        }
    }
}