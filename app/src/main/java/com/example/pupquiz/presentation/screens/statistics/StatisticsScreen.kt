package com.example.pupquiz.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.pupquiz.presentation.components.TopAppBarWithNavigation
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors

@Composable
fun StatisticsScreen(
    navController: NavController
) {
    val customColors = LocalCustomColors.current

    Scaffold(
        topBar = {
            TopAppBarWithNavigation(
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