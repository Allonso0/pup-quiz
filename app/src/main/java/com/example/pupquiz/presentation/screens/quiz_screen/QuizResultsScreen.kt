package com.example.pupquiz.presentation.screens.quiz_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pupquiz.presentation.components.TopAppBar
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun QuizResultsScreen(
    score: Int,
    timeElapsed: Int,
    correctAnswers: Int,
    totalQuestions: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    val customColors = LocalCustomColors.current

    Scaffold(
        topBar = {
            TopAppBar(
                label = "Game Over!"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customColors.background)
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Result",
                    fontFamily = MyCustomFont,
                    fontSize = 32.sp,
                    color = customColors.textColor
                )
            }


            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ResultItem("Total Score", "$score points")
                ResultItem("Time", "${timeElapsed}s")
                ResultItem("Correct Answers", "$correctAnswers/$totalQuestions")
                ResultItem("Accuracy", "${(correctAnswers.toFloat() / totalQuestions * 100).toInt()}%")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button (
                onClick = onRestart,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColors.secondBackground
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 24.sp,
                    fontFamily = MyCustomFont,
                    color = customColors.textColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onExit,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColors.secondBackground
                )
            ) {
                Text(
                    text = "Main Menu",
                    fontSize = 24.sp,
                    fontFamily = MyCustomFont,
                    color = customColors.textColor
                )
            }
        }
    }
}

@Composable
private fun ResultItem(label: String, value: String) {
    val customColors = LocalCustomColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = MyCustomFont,
            fontSize = 24.sp,
            color = customColors.textColor
        )
        Text(
            text = value,
            fontFamily = MyCustomFont,
            fontSize = 24.sp,
            color = customColors.textColor
        )
    }
}