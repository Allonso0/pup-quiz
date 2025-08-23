package com.example.pupquiz.presentation.screens.quiz_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pupquiz.domain.model.QuizQuestion
import com.example.pupquiz.presentation.components.LoadingScreen
import com.example.pupquiz.presentation.components.TopAppBarWithNavigation
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val customColors = LocalCustomColors.current
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isGameFinished) {
        QuizResultsScreen(
            score = uiState.score,
            timeElapsed = uiState.timeElapsed,
            correctAnswers = uiState.correctAnswers,
            totalQuestions = uiState.totalQuestions,
            onRestart = { viewModel.restartGame() },
            onExit = { navController.popBackStack() }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBarWithNavigation(
                    label = "Quiz",
                    onBackButtonClick = { navController.popBackStack() }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    containerColor = customColors.secondBackground
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        Text(
                            text = "Score",
                            fontFamily = MyCustomFont,
                            fontSize = 24.sp,
                            color = customColors.textColor
                        )

                        Text(
                            text = "${uiState.score}",
                            fontFamily = MyCustomFont,
                            fontSize = 28.sp,
                            color = customColors.textColor
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(customColors.background)
                    .padding(innerPadding)
            ) {
                if (uiState.questions.isNotEmpty()) {
                    val currentQuestion = uiState.questions[uiState.currentQuestion - 1]

                    QuizQuestionContent(
                        questionNum = uiState.currentQuestion,
                        timeElapsed = uiState.timeElapsed,
                        question = currentQuestion,
                        selectedAnswer = uiState.selectedAnswer,
                        isAnswered = uiState.isAnswered,
                        onAnswerSelected = viewModel::selectAnswer
                    )
                } else {
                    LoadingScreen()
                }
            }
        }
    }
}

@Composable
private fun QuizQuestionContent(
    questionNum: Int,
    timeElapsed: Int,
    question: QuizQuestion,
    selectedAnswer: String?,
    isAnswered: Boolean,
    onAnswerSelected: (String) -> Unit
) {
    val customColors = LocalCustomColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = "Question",
                    fontFamily = MyCustomFont,
                    fontSize = 18.sp,
                    color = customColors.textColor
                )

                Text(
                    text = "${questionNum}/10",
                    fontFamily = MyCustomFont,
                    fontSize = 24.sp,
                    color = customColors.textColor
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(customColors.secondBackground)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = "Time",
                    fontFamily = MyCustomFont,
                    fontSize = 18.sp,
                    color = customColors.textColor
                )

                Text(
                    text = timerFormat(timeElapsed),
                    fontFamily = MyCustomFont,
                    fontSize = 24.sp,
                    color = customColors.textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = question.imageURL,
            contentDescription = "Dog image",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        question.options.forEach { option ->
            val isSelected = selectedAnswer == option
            val isCorrect = option == question.correctAnswer

            AnswerButton(
                text = option.replaceFirstChar { it.uppercaseChar() },
                isSelected = isSelected,
                isCorrect = isCorrect,
                isAnswered = isAnswered,
                onClick = { onAnswerSelected(option) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AnswerButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    val customColors = LocalCustomColors.current
    val backgroundColor = when {
        !isAnswered -> customColors.secondBackground
        isCorrect -> Color.Green.copy(alpha = 0.3f)
        isSelected -> Color.Red.copy(alpha = 0.3f)
        else -> customColors.secondBackground
    }

    Button(
        onClick = onClick,
        enabled = !isAnswered,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
        )
    ) {
        Text(
            text = text,
            fontFamily = MyCustomFont,
            fontSize = 24.sp,
            color = customColors.textColor,
            textAlign = TextAlign.Center
        )
    }
}

private fun timerFormat(timer: Int): String {
    val seconds = timer % 60
    val minutes = (timer / 60) % 60
    return "%02d:%02d".format(minutes, seconds)
}