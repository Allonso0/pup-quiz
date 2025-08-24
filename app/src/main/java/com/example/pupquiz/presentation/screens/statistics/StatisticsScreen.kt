package com.example.pupquiz.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pupquiz.presentation.components.StatsCard
import com.example.pupquiz.presentation.components.TopAppBarWithNavigation
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val customColors = LocalCustomColors.current
    val statistics by viewModel.statistics.collectAsState()

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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatsCard(
                label = "Games played",
                value = "${statistics.totalGames}"
            )

            Text(
                text = "Score",
                fontFamily = MyCustomFont,
                fontSize = 32.sp,
                color = customColors.thirdTextColor
            )

            StatsCard(
                label = "Best score",
                value = "${statistics.bestScore} pts"
            )

            StatsCard(
                label = "Average score",
                value = "${statistics.averageScore} pts"
            )

            Text(
                text = "Time",
                fontFamily = MyCustomFont,
                fontSize = 32.sp,
                color = customColors.thirdTextColor
            )

            StatsCard(
                label = "Best time",
                value = "${statistics.bestTime}s"
            )

            StatsCard(
                label = "Average time",
                value = "${statistics.averageTime}s"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.cleatStats() },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColors.secondBackground
                )
            ) {
                Text(
                    text = "\nReset statistics\n",
                    fontFamily = MyCustomFont,
                    fontSize = 24.sp,
                    color = customColors.textColor
                )
            }
        }
    }
}