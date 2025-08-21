package com.example.pupquiz.presentation.screens.about_page

import com.example.pupquiz.domain.model.BreedDetails

sealed interface AboutScreenUiState {
    data object Loading : AboutScreenUiState
    data class Success(val details: BreedDetails) : AboutScreenUiState
    data class Error(val message: String) : AboutScreenUiState
}