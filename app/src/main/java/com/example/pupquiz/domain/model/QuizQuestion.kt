package com.example.pupquiz.domain.model

data class QuizQuestion (
    val imageURL: String,
    val correctAnswer: String,
    val options: List<String>,
    val breed: Breed
)