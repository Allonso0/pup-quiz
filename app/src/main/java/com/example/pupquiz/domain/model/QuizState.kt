package com.example.pupquiz.domain.model

data class QuizState(
    val questions: List<QuizQuestion> = emptyList(),
    val correctAnswers: Int = 0,
    val currentQuestion: Int = 1,
    val totalQuestions: Int = 10,
    val score: Int = 0,
    val timeElapsed: Int = 0,
    val selectedAnswer: String? = null,
    val isAnswered: Boolean = false,
    val currentQuestionStartTime: Long = System.currentTimeMillis(),
    val isGameFinished: Boolean = false
)
