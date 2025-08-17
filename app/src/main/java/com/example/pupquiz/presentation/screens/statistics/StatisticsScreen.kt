package com.example.pupquiz.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.pupquiz.presentation.components.TopAppBar
import com.example.pupquiz.presentation.screens.settings.SettingsScreen
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors

@Composable
fun StatisticsScreen(
    navController: NavController
) {
    val customColors = LocalCustomColors.current

    Scaffold(
        topBar = {
            TopAppBar(
                label = "Statistics",
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

        }
    }
}