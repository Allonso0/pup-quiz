package com.example.pupquiz.data.repository

import android.content.Context
import androidx.core.content.edit
import com.example.pupquiz.domain.model.GameStatistics
import com.example.pupquiz.domain.model.QuizQuestion
import com.example.pupquiz.domain.model.QuizState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class PreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )

    private val _themeUpdates = MutableSharedFlow<Boolean>(replay = 1)
    val themeUpdates: SharedFlow<Boolean> = _themeUpdates

    var isDarkThemeEnabled: Boolean
        get() = sharedPreferences.getBoolean("dark_theme", false)
        set(value) {
            sharedPreferences.edit { putBoolean("dark_theme", value) }
            _themeUpdates.tryEmit(value)
        }

    private val _soundUpdates = MutableSharedFlow<Boolean>(replay = 1)
    val soundUpdates: SharedFlow<Boolean> = _soundUpdates

    var isSoundEnabled: Boolean
        get() = sharedPreferences.getBoolean("sound_enabled", true)
        set(value) {
            sharedPreferences.edit { putBoolean("sound_enabled", value) }
            _soundUpdates.tryEmit(value)
        }

    fun saveQuizState(
        currentQuestion: Int,
        score: Int,
        timeElapsed: Int,
        selectedAnswer: String?,
        isAnswered: Boolean,
        questions: List<QuizQuestion>,
        correctAnswers: Int,
        isGameFinished: Boolean
    ) {
        sharedPreferences.edit {
            putInt("quiz_current_question", currentQuestion)
            putInt("quiz_score", score)
            putInt("quiz_time_elapsed", timeElapsed)
            putString("quiz_selected_answer", selectedAnswer)
            putBoolean("quiz_is_answered", isAnswered)
            putInt("quiz_correct_answers", correctAnswers)
            putBoolean("quiz_is_game_finished", isGameFinished)

            val gson = Gson()
            val questionsJson = gson.toJson(questions)
            putString("quiz_questions", questionsJson)
        }
    }

    fun getQuizState(): QuizState? {
        return try {
            val questionsJson = sharedPreferences.getString("quiz_questions", null)
                ?: return null

            val gson = Gson()
            val type = object : TypeToken<List<QuizQuestion>>() {}.type
            val questions = gson.fromJson<List<QuizQuestion>>(questionsJson, type)

            QuizState(
                currentQuestion = sharedPreferences.getInt("quiz_current_question", 1),
                score = sharedPreferences.getInt("quiz_score", 0),
                timeElapsed = sharedPreferences.getInt("quiz_time_elapsed", 0),
                selectedAnswer = sharedPreferences.getString("quiz_selected_answer", null),
                isAnswered = sharedPreferences.getBoolean("quiz_is_answered", false),
                questions = questions ?: emptyList(),
                correctAnswers = sharedPreferences.getInt("quiz_correct_answers", 0),
                isGameFinished = sharedPreferences.getBoolean("quiz_is_game_finished", false)
            )
        } catch (e: Exception) {
            null
        }
    }

    fun clearQuizState() {
        sharedPreferences.edit {
            remove("quiz_current_question")
            remove("quiz_score")
            remove("quiz_time_elapsed")
            remove("quiz_selected_answer")
            remove("quiz_is_answered")
            remove("quiz_questions")
            remove("quiz_correct_answers")
            remove("quiz_is_game_finished")
        }
    }

    fun getGameStats(): GameStatistics {
        return GameStatistics(
            totalGames = sharedPreferences.getInt("stats_total_games", 0),
            bestScore = sharedPreferences.getInt("stats_best_score", 0),
            averageScore = sharedPreferences.getInt("stats_average_score", 0),
            bestTime = sharedPreferences.getInt("stats_best_time", 0),
            averageTime = sharedPreferences.getInt("stats_average_time", 0),
        )
    }

    fun saveGameStats(score: Int, time: Int) {
        val currentStats = getGameStats()

        val newTotalGames = currentStats.totalGames + 1
        val newBestScore = maxOf(currentStats.bestScore, score)
        val newBestTime = if (currentStats.bestTime == 0) time else minOf(currentStats.bestTime, time)

        val newAverageScore = ((currentStats.averageScore * currentStats.totalGames) + score) / newTotalGames
        val newAverageTime = ((currentStats.averageTime * currentStats.totalGames) + time) / newTotalGames

        sharedPreferences.edit {
            putInt("stats_total_games", newTotalGames)
            putInt("stats_best_score", newBestScore)
            putInt("stats_average_score", newAverageScore)
            putInt("stats_best_time", newBestTime)
            putInt("stats_average_time", newAverageTime)
        }
    }

    fun clearStats() {
        sharedPreferences.edit {
            remove("stats_total_games")
            remove("stats_best_score")
            remove("stats_average_score")
            remove("stats_best_time")
            remove("stats_average_time")
        }
    }
}