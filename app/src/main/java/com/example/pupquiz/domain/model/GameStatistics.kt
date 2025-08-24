package com.example.pupquiz.domain.model

data class GameStatistics(
    val totalGames: Int = 0,
    val bestScore: Int = 0,
    val averageScore: Int = 0,
    val bestTime: Int = 0,
    val averageTime: Int = 0
)