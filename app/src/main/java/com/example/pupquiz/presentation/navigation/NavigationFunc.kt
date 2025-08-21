package com.example.pupquiz.presentation.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pupquiz.presentation.screens.about_page.AboutScreen
import com.example.pupquiz.presentation.screens.learn_screen.LearnScreen
import com.example.pupquiz.presentation.screens.main_screen.MainScreen
import com.example.pupquiz.presentation.screens.quiz_screen.QuizScreen
import com.example.pupquiz.presentation.screens.settings.SettingsScreen
import com.example.pupquiz.presentation.screens.statistics.StatisticsScreen

@Composable
fun NavigationFunc() {
    val navController = rememberNavController()

    Surface {
        NavHost(
            navController = navController,
            startDestination = "MainScreen"
        ) {
            composable("MainScreen") {
                MainScreen(
                    navController = navController
                )
            }

            composable("QuizScreen") {
                QuizScreen(
                    navController = navController
                )
            }

            composable("LearnScreen") {
                LearnScreen(
                    navController = navController
                )
            }

            composable("AboutScreen/{breedName}") { backStackEntry ->
                val breedName = backStackEntry.arguments?.getString("breedName")
                AboutScreen(
                    breedName = breedName,
                    navController = navController
                )
            }

            composable("SettingsScreen") {
                SettingsScreen(
                    navController = navController
                )
            }

            composable("StatisticsScreen") {
                StatisticsScreen(
                    navController = navController
                )
            }
        }
    }
}