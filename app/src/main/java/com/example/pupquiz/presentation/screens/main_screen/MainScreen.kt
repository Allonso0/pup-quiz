package com.example.pupquiz.presentation.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pupquiz.presentation.components.Logo
import com.example.pupquiz.presentation.components.MainScreenButton
import com.example.pupquiz.presentation.screens.statistics.StatisticsScreen
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun MainScreen(
    navController: NavController
) {
    val customColors = LocalCustomColors.current

    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customColors.background)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()

            Spacer(modifier = Modifier.height(24.dp))

            MainScreenButton(
                buttonText = "Quiz",
                onButtonClick = { navController.navigate("QuizScreen") }
            )

            MainScreenButton(
                buttonText = "Learn",
                onButtonClick = { navController.navigate("LearnScreen") }
            )

            MainScreenButton(
                buttonText = "Statistics",
                onButtonClick = { navController.navigate("StatisticsScreen") }
            )

            MainScreenButton(
                buttonText = "Settings",
                onButtonClick = { navController.navigate("SettingsScreen") }
            )

        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Pup Quiz v1.0",
                color = customColors.secondTextColor,
                fontFamily = MyCustomFont,
                fontSize = 24.sp
            )
        }

    }
}