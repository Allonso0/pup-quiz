package com.example.pupquiz.presentation.screens.quiz_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupquiz.data.repository.PreferencesManager
import com.example.pupquiz.domain.model.QuizState
import com.example.pupquiz.domain.usecase.GenerateQuiz
import com.example.pupquiz.domain.usecase.PlayGameOverSound
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val prefs: PreferencesManager,
    private val generateQuiz: GenerateQuiz,
    private val playGameOverSound: PlayGameOverSound
) : ViewModel() {

    private val _uiState = MutableStateFlow(loadSavedState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
        if (_uiState.value.questions.isEmpty()) {
            generateNewQuiz()
        }
    }

    private fun generateNewQuiz() {
        viewModelScope.launch {
            val questions = generateQuiz()
            _uiState.value = QuizState(questions = questions)
            saveState()
        }
    }

    private fun loadSavedState(): QuizState {
        return prefs.getQuizState() ?: QuizState()
    }

    private fun saveState() {
        val state = _uiState.value
        prefs.saveQuizState(
            state.currentQuestion,
            state.score,
            state.timeElapsed,
            state.selectedAnswer,
            state.isAnswered,
            state.questions,
            state.correctAnswers,
            state.isGameFinished
        )
    }

    fun restartGame() {
        timerJob?.cancel()
        _uiState.value = QuizState()
        generateNewQuiz()
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (!_uiState.value.isAnswered && !_uiState.value.isGameFinished) {
                    _uiState.value = _uiState.value.copy(
                        timeElapsed = _uiState.value.timeElapsed + 1
                    )
                    saveState()
                }
            }
        }
    }

    fun selectAnswer(answer: String) {
        if (_uiState.value.isAnswered) return

        val state = _uiState.value
        val timeSpent = ((System.currentTimeMillis() - state.currentQuestionStartTime) / 1000).toInt()

        val isCorrect = answer == state.questions[state.currentQuestion - 1].correctAnswer

        val points = if (isCorrect) {
            maxOf(50, 100 - 2 * timeSpent)
        } else {
            0
        }

        _uiState.value = state.copy(
            selectedAnswer = answer,
            isAnswered = true,
            score = state.score + points,
            correctAnswers = state.correctAnswers + if (isCorrect) 1 else 0
        )

        saveState()

        viewModelScope.launch {
            delay(3000)
            moveToNextQuestion()
        }
    }

    private fun moveToNextQuestion() {
        val state = _uiState.value
        if (state.currentQuestion >= state.totalQuestions) {
            playGameOverSound()

            prefs.saveGameStats(state.score, state.timeElapsed)

            _uiState.value = state.copy(isGameFinished = true)
        } else {
            _uiState.value = state.copy(
                currentQuestion = state.currentQuestion + 1,
                selectedAnswer = null,
                isAnswered = false,
                currentQuestionStartTime = System.currentTimeMillis()
            )
        }
        saveState()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}