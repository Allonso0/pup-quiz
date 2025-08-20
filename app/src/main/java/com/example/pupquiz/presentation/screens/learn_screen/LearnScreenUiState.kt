package com.example.pupquiz.presentation.screens.learn_screen

import com.example.pupquiz.domain.model.Breed

sealed interface LearnScreenUiState {
    data object Loading : LearnScreenUiState

    data class Success(
        val breeds: List<Breed>,
        val isOnline: Boolean
    ) : LearnScreenUiState

    data class Empty(
        val message: String,
        val isOnline: Boolean
    ) : LearnScreenUiState

    data class Error(
        val message: String,
        val isOnline: Boolean
    ) : LearnScreenUiState
}