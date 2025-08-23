package com.example.pupquiz.presentation.screens.about_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pupquiz.domain.model.BreedDetails
import com.example.pupquiz.presentation.components.LoadingScreen
import com.example.pupquiz.presentation.components.TopAppBarWithNavigation
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun AboutScreen(
    breedName: String?,
    navController: NavController,
    viewModel: AboutScreenViewModel = hiltViewModel()
) {
    val customColors = LocalCustomColors.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(breedName) {
        if (breedName != null) {
            viewModel.loadBreedDetails(breedName)
        }
    }

    Scaffold(
        topBar = {
            TopAppBarWithNavigation(
                label = "About",
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
            when(val state = uiState) {
                is AboutScreenUiState.Loading -> LoadingScreen()
                is AboutScreenUiState.Success -> SuccessState(state.details)
                is AboutScreenUiState.Error -> ErrorState(state.message)
            }
        }
    }
}

@Composable
private fun SuccessState(
    details: BreedDetails
) {
    val customColors = LocalCustomColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .clip(RoundedCornerShape(24.dp))
                .background(customColors.secondBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = details.breed.name.replaceFirstChar { it.uppercaseChar() },
                fontFamily = MyCustomFont,
                fontSize = 28.sp,
                color = customColors.textColor,
                textAlign = TextAlign.Center
            )
        }

        AsyncImage(
            model = details.imageURL,
            contentDescription = details.breed.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        if (details.breed.subBreeds.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No sub-breeds",
                    fontFamily = MyCustomFont,
                    fontSize = 24.sp,
                    color = customColors.textColor,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground)
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Sub-breeds:",
                    fontFamily = MyCustomFont,
                    fontSize = 24.sp,
                    color = customColors.textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(details.breed.subBreeds) { subBreed ->
                        Text(
                            text = "• ${subBreed.replaceFirstChar { it.uppercaseChar() }}",
                            fontFamily = MyCustomFont,
                            fontSize = 24.sp,
                            color = customColors.textColor
                        )
                    }
                }
            }
        }
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