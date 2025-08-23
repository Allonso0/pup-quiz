package com.example.pupquiz.presentation.screens.learn_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pupquiz.domain.model.Breed
import com.example.pupquiz.presentation.components.BreedCard
import com.example.pupquiz.presentation.components.LoadingScreen
import com.example.pupquiz.presentation.components.TopAppBarWithNavigation
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun LearnScreen(
    navController: NavController,
    viewModel: LearnScreenViewModel = hiltViewModel()
) {
    val customColors = LocalCustomColors.current
    val uiState by viewModel.uiState.collectAsState()

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.loadBreeds()
    }

    Scaffold(
        topBar = {
            TopAppBarWithNavigation(
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
                .padding(24.dp)
        ) {
            when (val state = uiState) {
                is LearnScreenUiState.Loading -> LoadingScreen()
                is LearnScreenUiState.Success -> SuccessState(
                    breeds = state.breeds,
                    onCardClick = { breedName ->
                        navController.navigate("AboutScreen/$breedName")
                    },
                    lazyListState = lazyListState)
                is LearnScreenUiState.Empty -> EmptyState()
                is LearnScreenUiState.Error -> ErrorState(message = state.message)
            }
        }
    }
}

// TODO: закончить элементы экранов.
@Composable
private fun SuccessState(
    breeds: List<Breed>,
    onCardClick: (String) -> Unit,
    lazyListState: LazyListState
) {
    val customColors = LocalCustomColors.current

    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Click on the card to learn more about the breed",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontFamily = MyCustomFont,
                color = customColors.secondTextColor
            )
        }
        items(breeds) { breed ->
            BreedCard(
                breedName = breed.name,
                onItemClick = { onCardClick(breed.name) }
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Empty")
    }
}

@Composable
private fun ErrorState(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}