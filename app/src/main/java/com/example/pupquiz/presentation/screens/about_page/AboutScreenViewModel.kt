package com.example.pupquiz.presentation.screens.about_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.domain.usecase.GetBreedDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutScreenViewModel @Inject constructor(
    private val getBreedDetails: GetBreedDetails
) : ViewModel() {

    private val _uiState = MutableStateFlow<AboutScreenUiState>(AboutScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadBreedDetails(breedName: String) {
        viewModelScope.launch {
            _uiState.value = AboutScreenUiState.Loading

            try {
                val details = getBreedDetails(breedName)
                _uiState.value = AboutScreenUiState.Success(details)
            } catch (e: Exception) {
                _uiState.value = AboutScreenUiState.Error(
                    message = "Failed to load breed details: ${e.message}"
                )
            }
        }
    }
}