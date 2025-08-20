package com.example.pupquiz.presentation.screens.learn_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pupquiz.domain.model.Breed
import com.example.pupquiz.presentation.components.TopAppBar
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors

@Composable
fun LearnScreen(
    navController: NavController,
    viewModel: LearnScreenViewModel = hiltViewModel()
) {
    val customColors = LocalCustomColors.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBreeds()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                label = "Learn",
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customColors.background)
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is LearnScreenUiState.Loading -> LoadingState()
                is LearnScreenUiState.Success -> SuccessState(
                    breeds = state.breeds,
                    isOnline = state.isOnline)
                is LearnScreenUiState.Empty -> EmptyState(
                    message = state.message,
                    isOnline = state.isOnline)
                is LearnScreenUiState.Error -> EmptyState(
                    message = state.message,
                    isOnline = state.isOnline)
            }
        }
    }
}

// TODO: закончить элементы экранов.
@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessState(breeds: List<Breed>, isOnline: Boolean) {
    LazyColumn {
        items(breeds) { breed ->
            Text(breed.name)
        }
    }
}

@Composable
private fun EmptyState(message: String, isOnline: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Empty")
    }
}

@Composable
private fun ErrorState(message: String, isOnline: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}