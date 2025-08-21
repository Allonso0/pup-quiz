package com.example.pupquiz.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors

@Composable
fun LoadingScreen() {
    val customColors = LocalCustomColors.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = customColors.secondBackground
        )
    }
}