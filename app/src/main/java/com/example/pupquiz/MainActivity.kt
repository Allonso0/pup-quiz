package com.example.pupquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.presentation.navigation.NavigationFunc
import com.example.pupquiz.presentation.screens.settings.SettingsViewModel
import com.example.pupquiz.presentation.ui.theme.PupQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val isDarkThemeEnabled by settingsViewModel.isDarkThemeEnabled.collectAsState()

            PupQuizTheme(darkTheme = isDarkThemeEnabled) {
                NavigationFunc()
            }
        }
    }
}